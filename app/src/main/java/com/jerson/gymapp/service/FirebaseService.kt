package com.jerson.gymapp.service

import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.jerson.gymapp.Utils.Util
import com.jerson.gymapp.model.Exercicio
import com.jerson.gymapp.model.Treino

class FirebaseService {

    // Firebase Authentication instance
    private var firebase = FirebaseAuth.getInstance()
    // Firestore instance
    private var db = FirebaseFirestore.getInstance()

    // Get current user
    fun user() = firebase.currentUser

    // Get current user ID
    private fun currentUser(): String {
        return firebase.currentUser!!.uid
    }

    /**
     * Grava um exercício no Firestore.
     *
     * @param exercicio O objeto Exercicio a ser gravado.
     * @param callback Callback a ser chamado após a conclusão da operação.
     */
    fun gravarExercicio(exercicio: Exercicio, callback: () -> Unit) {
        val exercicioDB = hashMapOf(
            "nome" to exercicio.nome,
            "descricao" to exercicio.descricao,
            "imagem" to exercicio.imagem.toString()
        )

        db.collection("exercicio").document(exercicio.nome)
            .set(exercicioDB)
            .addOnCompleteListener {
                Log.d("db", "Exercicio salvo com sucesso")
                callback()
            }.addOnFailureListener {
                Log.d("o erro ta aqui", it.message.toString())
            }
    }

    /**
     * Grava uma lista de IDs de exercícios associada a um nome de treino no Firestore.
     *
     * @param nomeTreino Nome do treino.
     * @param nomeExercicios Lista de nomes de exercícios a serem associados.
     * @param callback Callback a ser chamado após a conclusão da operação.
     */
    fun gravarListIdExercicio(nomeTreino: String, nomeExercicios: List<String>, callback: () -> Unit) {
        val mapExercicios = hashMapOf<String, List<String>>(
            "exercicios" to nomeExercicios.toList()
        )
        db.collection("Usuarios")
            .document(currentUser())
            .collection("Treino")
            .document(nomeTreino)
            .set(
                mapExercicios,
                SetOptions.merge()
            ).addOnCompleteListener {
                Log.d("db", "lista de exercicios salvo com sucesso")
                callback()
            }.addOnFailureListener {
                Log.d("db", it.message.toString())
            }
    }

    /**
     * Grava um treino no Firestore.
     *
     * @param treino Objeto Treino a ser gravado.
     * @param callback Callback a ser chamado após a conclusão da operação.
     */
    fun gravarTreino(treino: Treino, callback: () -> Unit) {
        val treinoDB = hashMapOf(
            "nome" to treino.nome,
            "data" to treino.data,
            "descricao" to treino.descricao
        )
        db.collection("Usuarios")
            .document(currentUser())
            .collection("Treinos")
            .document(treino.nome)
            .set(treinoDB)
            .addOnCompleteListener {
                Log.d("db", "Treino salvo com sucesso")
                callback()
            }.addOnFailureListener {
                Log.d("db", it.message.toString())
            }
    }

    /**
     * Obtém todos os exercícios do Firestore.
     *
     * @param callback Callback a ser chamado com a lista de exercícios obtidos.
     */
    fun getAllExercicios(callback: (exercicioList: MutableList<Exercicio>) -> Unit) {
        val exercicioList: MutableList<Exercicio> = mutableListOf()
        db.collection("exercicio").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    exercicioList.add(
                        Exercicio(
                            document.data["nome"].toString(),
                            document.data["descricao"].toString(),
                            Uri.parse(document.data["nome"].toString()),
                            false
                        )
                    )
                }
                callback(exercicioList)
            }
            .addOnFailureListener { exception ->
                println("Erro ao recuperar documentos: $exception")
            }
    }

    // Grava o usuário atual no Firestore
    private fun gravarUsuario() {
        db.collection("Usuarios")
            .document(currentUser())
            .set(currentUser())
            .addOnCompleteListener {
                Log.d("db", "lista de exercicios salvo com sucesso")
            }.addOnFailureListener {
                Log.d("db", it.message.toString())
            }
    }

    /**
     * Realiza o cadastro de um usuário com o Firebase Authentication.
     *
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @param view View para exibição do feedback.
     * @return Retorna true se o cadastro for bem-sucedido, caso contrário, retorna false.
     */
    fun cadastroUsuario(email: String, senha: String, view: View): Boolean {
        var resp = true

        firebase.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
            if (cadastro.isSuccessful) {
                val snackbar = Snackbar.make(view, "Cadastro feito com sucesso",
                    Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.show()
                //gravarUsuario()
            }

        }.addOnFailureListener { exception ->
            exceptionFirebase(exception, view)
            resp = false
        }
        return resp
    }

    /**
     * Realiza o login de um usuário com o Firebase Authentication.
     *
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @param view View para exibição do feedback.
     * @return Retorna true se o login for bem-sucedido, caso contrário, retorna false.
     */
    fun loginUsuario(email: String, senha: String, view: View): Boolean {
        var resp = true

        firebase.signInWithEmailAndPassword(email, senha).addOnCompleteListener { login ->
            if (login.isSuccessful) {
                val snackbar = Snackbar.make(view, "Login feito com sucesso", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.show()
            }
        }.addOnFailureListener { exception ->
            exceptionFirebase(exception, view)
            resp = false
        }
        return resp
    }

    // Realiza o logout do usuário atual
    fun deslogarUsuario() {
        if (firebase.currentUser != null) {
            firebase.signOut()
        }
    }

    /**
     * Exibe mensagens de erro baseadas nas exceções do Firebase.
     *
     * @param exception Exceção recebida.
     * @param view View para exibição do feedback.
     */
    private fun exceptionFirebase(exception: Exception, view: View) {
        val mensagem = when (exception) {
            is FirebaseNetworkException -> "Sem conexão com a Internet, verifique sua rede!"
            is FirebaseAuthWeakPasswordException -> "Senha com no mínimo 6 caracteres"
            is FirebaseAuthUserCollisionException -> "Usuário já cadastrado!"
            is FirebaseAuthInvalidCredentialsException -> "Email inválido!, Verifique seu Email!"
            else -> "Erro na aplicação"
        }
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    fun getAllTreinos(callback: (List<Treino>, error: Exception?) -> Unit) {
        val treinoList :MutableList<Treino> = mutableListOf()
        db.collection("Usuarios")
            .document(currentUser())
            .collection("Treinos")
            .get()
            .addOnSuccessListener {documents ->
                for (document in documents){
                    treinoList.add(
                        Treino(
                            document.getString("nome")!!,
                            Util.converterTextoParaTimestamp(document.getString("data")!!),
                            ""
                        )
                    )

                }
                callback(treinoList,null)
            }.addOnFailureListener{
                callback(listOf(),it)
            }
    }


}

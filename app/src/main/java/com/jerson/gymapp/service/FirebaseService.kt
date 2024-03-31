package com.jerson.gymapp.service



import android.graphics.Color
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.jerson.gymapp.model.Exercicio
import com.jerson.gymapp.model.Treino


class FirebaseService {

       private lateinit var firebase: FirebaseAuth
       private var db = FirebaseFirestore.getInstance()

       private fun init(){
              firebase = FirebaseAuth.getInstance()
       }

       private fun currentUser(): String {
              init()
              return firebase.currentUser!!.uid
       }


       fun gravarExercicio(exercicio: Exercicio,callback: () -> Unit){

              db.collection("Exercício").document(exercicio.nome)
                     .set(exercicio).addOnCompleteListener {
                            Log.d("db","Exercicio salvo com sucesso")
                            callback()
                     }.addOnFailureListener{
                            Log.d("db",it.message.toString())
                     }
       }

       fun gravarListIdExercicio(nomeTreino: String,nomeExercicios: List<String>,callback: () -> Unit){
              db.collection("Usuarios")
                     .document(currentUser())
                     .collection("Treino")
                     .document(nomeTreino)
                     .set(
                       nomeExercicios,
                            SetOptions.merge()
                     ).addOnCompleteListener {
                            Log.d("db","lista de exercicios salvo com sucesso")
                            callback()
                     }.addOnFailureListener{
                            Log.d("db",it.message.toString())
                     }
       }

       fun gravarTreino(treino: Treino,callback: () -> Unit){
              db.collection("Usuarios")
                     .document(currentUser())
                     .collection("Treino")
                     .document(treino.nome)
                     .set(treino)
                     .addOnCompleteListener {
                            Log.d("db","Treino salvo com sucesso")
                            callback()
                     }.addOnFailureListener{
                            Log.d("db",it.message.toString())
                     }

       }

       fun getAllExercicios(callback: ( exercicioList : MutableList<Exercicio>) ->Unit){

              db.collection("Exercicios")
                     .get()
                     .addOnSuccessListener { result ->
                            val exercicioList : MutableList<Exercicio> = mutableListOf()
                            for (document in result) {
                                   exercicioList.add(Exercicio(
                                          document.getString("nome")!!,
                                          document.getString("descricao")!!,
                                          document.getString("imagem"),
                                          selecionado = false
                                   ))
                            }
                            callback(exercicioList)
                     }
                     .addOnFailureListener { exception ->
                            Log.d("db", "Error getting documents: ", exception)
                     }
       }

       private fun gravarUsuario() {
              db.collection("Usuarios")
                     .document(currentUser())
                     .set(currentUser())
                     .addOnCompleteListener {
                            Log.d("db","lista de exercicios salvo com sucesso")
                     }.addOnFailureListener{
                            Log.d("db",it.message.toString())
                     }
       }



       fun cadastroUsuario(email: String, senha: String, nome: String, view: View) : Boolean{
              var resp = true
              init()
              firebase.createUserWithEmailAndPassword(email,senha).addOnCompleteListener{ cadastro ->
                     if (cadastro.isSuccessful){
                            val snackbar = Snackbar.make(view,"Cadastro feito com sucesso",
                                   Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                            gravarUsuario()
                     }

              }.addOnFailureListener{exception ->
                     exceptionFirebase(exception,view)
                     resp = false
              }
              return resp
       }
       fun loginUsuario(email: String,senha: String,view: View) : Boolean{
              var resp = true
              init()
              firebase.signInWithEmailAndPassword(email,senha).addOnCompleteListener {login ->
                     if (login.isSuccessful){
                            val snackbar = Snackbar.make(view,"Login feito com sucesso", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                     }
              }.addOnFailureListener{exception ->
                     exceptionFirebase(exception,view)
                     resp = false
              }
              return resp
       }
       fun deslogarUsuario(){
              init()
              firebase.signOut()
       }
       private fun exceptionFirebase(exception: Exception,view: View) {
              val mensagem = when(exception){
                     is FirebaseNetworkException -> "Sem conexão com a Internet, verifique sua rede!"
                     is FirebaseAuthWeakPasswordException -> "Senha com no mínimo 6 caracteres"
                     is FirebaseAuthUserCollisionException -> "Usuário já cadastrado!"
                     is FirebaseAuthInvalidCredentialsException -> "Email inválido!, Verifique seu Email!"
                     else                                        -> "Erro na aplicação"
              }
              val snackbar = Snackbar.make(view,mensagem, Snackbar.LENGTH_SHORT)
              snackbar.setBackgroundTint(Color.RED)
              snackbar.show()
       }




}
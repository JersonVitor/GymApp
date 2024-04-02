package com.jerson.gymapp.service



import android.graphics.Color
import android.net.Uri
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jerson.gymapp.model.Exercicio
import com.jerson.gymapp.model.Treino


class FirebaseService {

       private  var firebase = FirebaseAuth.getInstance()
       private var db = FirebaseFirestore.getInstance()



       fun user() = firebase.currentUser
       private fun currentUser(): String {
              return firebase.currentUser!!.uid
       }


       fun gravarExercicio(exercicio: Exercicio,callback: () -> Unit){
              val exercicioDB = hashMapOf(
                     "nome" to exercicio.nome,
                     "descricao" to exercicio.descricao,
                     "imagem" to exercicio.imagem.toString()

              )

              db.collection("exercicio").document(exercicio.nome)
                     .set(
                            exercicioDB
                     ).addOnCompleteListener {
                            Log.d("db","Exercicio salvo com sucesso")
                            callback()
                     }.addOnFailureListener{
                            Log.d("o erro ta aqui",it.message.toString())
                     }
       }

       fun gravarListIdExercicio(nomeTreino: String,nomeExercicios: List<String>,callback: () -> Unit){
              val mapExercicios = hashMapOf<String,List<String>>(
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
                            Log.d("db","lista de exercicios salvo com sucesso")
                            callback()
                     }.addOnFailureListener{
                            Log.d("db",it.message.toString())
                     }
       }

       fun gravarTreino(treino: Treino,callback: () -> Unit){

             val treinoDB = hashMapOf(
                     "nome" to treino.nome,
                     "data" to treino.data,
                     "descricao" to treino.descricao
              )
              db.collection("Usuarios")
                     .document(currentUser())
                     .collection("Treino")
                     .document(treino.nome)
                     .set(treinoDB)
                     .addOnCompleteListener {
                            Log.d("db","Treino salvo com sucesso")
                            callback()
                     }.addOnFailureListener{
                            Log.d("db",it.message.toString())
                     }

       }

       fun getAllExercicios(callback: ( exercicioList : MutableList<Exercicio>) ->Unit) {
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



       fun cadastroUsuario(email: String, senha: String, view: View) : Boolean{
              var resp = true

              firebase.createUserWithEmailAndPassword(email,senha).addOnCompleteListener{ cadastro ->
                     if (cadastro.isSuccessful){
                            val snackbar = Snackbar.make(view,"Cadastro feito com sucesso",
                                   Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                            //gravarUsuario()
                     }

              }.addOnFailureListener{exception ->
                     exceptionFirebase(exception,view)
                     resp = false
              }
              return resp
       }
       fun loginUsuario(email: String,senha: String,view: View) : Boolean{
              var resp = true

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
              if(firebase.currentUser != null){
                     firebase.signOut()
              }
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

       fun lerTeste() {
              TODO("Not yet implemented")
       }


}
package com.jerson.gymapp.service

import android.content.Intent
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FirebaseService {




    companion object{

        private val firebase = FirebaseAuth.getInstance()

        fun cadastroUsuario(email: String, senha: String, view: View) : Boolean{
            var resp = true
            firebase.createUserWithEmailAndPassword(email,senha).addOnCompleteListener{ cadastro ->
                if (cadastro.isSuccessful){
                    val snackbar = Snackbar.make(view,"Cadastro feito com sucesso",Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.GREEN)
                    snackbar.show()
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
                    val snackbar = Snackbar.make(view,"Login feito com sucesso",Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.GREEN)
                    snackbar.show()
                }
            }.addOnFailureListener{exception ->
                exceptionFirebase(exception,view)
                resp = false
            }
            return resp
        }
        fun DeslogarUsuario(){
            firebase.signOut()
        }
        private fun exceptionFirebase(exception: Exception,view: View) {
            val mensagem = when(exception){
                is FirebaseNetworkException                 -> "Sem conexão com a Internet, verifique sua rede!"
                is FirebaseAuthWeakPasswordException        -> "Senha com no mínimo 6 caracteres"
                is FirebaseAuthUserCollisionException       -> "Usuário já cadastrado!"
                is FirebaseAuthInvalidCredentialsException  -> "Email inválido!, Verifique seu Email!"
                else                                        -> "Erro na aplicação"
            }
            val snackbar = Snackbar.make(view,mensagem,Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        }


    }


}
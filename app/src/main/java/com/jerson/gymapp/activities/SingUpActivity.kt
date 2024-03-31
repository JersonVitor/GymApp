package com.jerson.gymapp.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.jerson.gymapp.databinding.ActivitySingUpBinding
import com.jerson.gymapp.service.FirebaseService


class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var firebase : FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        firebase = FirebaseService()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textLogin.setOnClickListener {
            val telaLogin = Intent(this, SignInActivity::class.java)
            startActivity(telaLogin)
        }

        binding.btnCadastroSubmit.setOnClickListener {view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val nome = binding.editNome.text.toString()
            if(email.isEmpty() || senha.isEmpty() || nome.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.show()
            }else{
                if(firebase.cadastroUsuario(email, senha,nome, view)) navegarTelaHome()
            }
        }

    }
    private fun navegarTelaHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.jerson.gymapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jerson.gymapp.databinding.ActivitySignInBinding
import com.jerson.gymapp.service.FirebaseService


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLoginSubmit.setOnClickListener { view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if(senha.isEmpty() || email.isEmpty() ) {
                val snackbar = Snackbar.make(view,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                if (FirebaseService.loginUsuario(email, senha, view)) navegarTelaHome()
            }
        }

        binding.textCadastro.setOnClickListener {
            val telaCadastrar = Intent(this, SingUpActivity::class.java)
            startActivity(telaCadastrar)
        }
    }
    private fun navegarTelaHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
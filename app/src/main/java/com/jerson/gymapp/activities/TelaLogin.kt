package com.jerson.gymapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.jerson.gymapp.databinding.ActivityTelaLoginBinding
import com.jerson.gymapp.service.FirebaseService



class TelaLogin : AppCompatActivity() {

    private lateinit var binding: ActivityTelaLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTelaLoginBinding.inflate(layoutInflater)
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
            val telaCadastrar = Intent(this,TelaCadastrar::class.java)
            startActivity(telaCadastrar)
        }
    }
    private fun navegarTelaHome(){
        val intent = Intent(this,TelaHome::class.java)
        startActivity(intent)
        finish()
    }
}
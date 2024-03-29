package com.jerson.gymapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.jerson.gymapp.databinding.ActivityTelaCadastrarBinding
import com.jerson.gymapp.service.FirebaseService


class TelaCadastrar : AppCompatActivity() {

    private lateinit var binding: ActivityTelaCadastrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTelaCadastrarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textLogin.setOnClickListener {
            val telaLogin = Intent(this,TelaLogin::class.java)
            startActivity(telaLogin)
        }

        binding.btnCadastroSubmit.setOnClickListener {view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.show()
            }else{
                if(FirebaseService.cadastroUsuario(email, senha, view)) navegarTelaHome()
            }
        }

    }
    private fun navegarTelaHome(){
        val intent = Intent(this,TelaHome::class.java)
        startActivity(intent)
        finish()
    }
}
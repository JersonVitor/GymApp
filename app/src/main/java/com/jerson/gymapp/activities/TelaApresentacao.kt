package com.jerson.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

import com.jerson.gymapp.databinding.ActivityTelaApresentacaoBinding

class TelaApresentacao : AppCompatActivity() {

    private lateinit var binding: ActivityTelaApresentacaoBinding




    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            navegarTelaHome()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTelaApresentacaoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener{
            val telaLogin = Intent(this, TelaLogin::class.java)
            startActivity(telaLogin)
        }
        binding.btnCadastrar.setOnClickListener {
            val telaCadastrar = Intent(this, TelaCadastrar::class.java)
            startActivity(telaCadastrar)
        }
    }
    private fun navegarTelaHome(){
        val intent = Intent(this,TelaHome::class.java)
        startActivity(intent)
        finish()
    }
}
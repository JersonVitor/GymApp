package com.jerson.gymapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.jerson.gymapp.databinding.ActivityApresentacaoBinding
import com.jerson.gymapp.service.FirebaseService


class ApresentacaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApresentacaoBinding
    private lateinit var firebase : FirebaseService

    override fun onStart() {
        super.onStart()
        firebase = FirebaseService()

        if(firebase.user() != null){
            navegarTelaHome()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityApresentacaoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener{
            val telaLogin = Intent(this, SignInActivity::class.java)
            startActivity(telaLogin)
        }
        binding.btnCadastrar.setOnClickListener {
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
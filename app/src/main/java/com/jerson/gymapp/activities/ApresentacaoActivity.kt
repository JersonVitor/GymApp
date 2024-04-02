package com.jerson.gymapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.jerson.gymapp.databinding.ActivityApresentacaoBinding
import com.jerson.gymapp.service.FirebaseService

/**
 * Activity de apresentação.
 * Verifica se o usuário já está logado e navega para a tela principal se estiver.
 */
class ApresentacaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApresentacaoBinding
    private lateinit var firebase: FirebaseService

    override fun onStart() {
        super.onStart()
        firebase = FirebaseService()

        // Verifica se o usuário está logado e navega para a tela principal
        if (firebase.user() != null) {
            navegarTelaHome()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApresentacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração dos cliques dos botões
        binding.btnLogin.setOnClickListener{
            val telaLogin = Intent(this, SignInActivity::class.java)
            startActivity(telaLogin)
        }
        binding.btnCadastrar.setOnClickListener {
            val telaCadastrar = Intent(this, SingUpActivity::class.java)
            startActivity(telaCadastrar)
        }
    }

    /**
     * Navega para a tela principal.
     */
    private fun navegarTelaHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

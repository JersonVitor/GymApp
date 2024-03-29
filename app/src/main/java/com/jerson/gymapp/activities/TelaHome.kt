package com.jerson.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jerson.gymapp.databinding.ActivityTelaHomeBinding
import com.jerson.gymapp.service.FirebaseService


class TelaHome : AppCompatActivity() {

    private lateinit var binding: ActivityTelaHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTelaHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        /**
        binding.btn_Deslogar.setOnClickListener {
        FirebaseService.DeslogarUsuario()
        val telaApresentacao = Intent(this,TelaApresentacao::class.java)
        startActivity(telaApresentacao)
        finish()
        }
         **/
    }

}
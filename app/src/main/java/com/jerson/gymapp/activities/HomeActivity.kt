package com.jerson.gymapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jerson.gymapp.databinding.ActivityHomeBinding
import com.jerson.gymapp.service.FirebaseService


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
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
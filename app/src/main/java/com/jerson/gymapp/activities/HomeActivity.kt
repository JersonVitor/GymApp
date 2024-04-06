package com.jerson.gymapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jerson.gymapp.databinding.ActivityHomeBinding
import com.jerson.gymapp.service.FirebaseService


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebase: FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        firebase = FirebaseService()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*binding.btnCdExercicio.setOnClickListener {
            val cadExercicioActivity = Intent(this,CadExercicioActivity::class.java)
            startActivity(cadExercicioActivity)

        }
        binding.btnCdTreino.setOnClickListener {
            val cadExercicioActivity = Intent(this,CadTreinoActivity::class.java)
            startActivity(cadExercicioActivity)

        }


        binding.btnDeslogar.setOnClickListener {
            firebase.deslogarUsuario()
            val telaApresentacao = Intent(this,ApresentacaoActivity::class.java)
            startActivity(telaApresentacao)
            finish()
        }
*/

    }

}
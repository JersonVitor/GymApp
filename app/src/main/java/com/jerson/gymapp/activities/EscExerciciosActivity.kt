package com.jerson.gymapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jerson.gymapp.adapter.ExercicioAdapter
import com.jerson.gymapp.databinding.ActivityEscExerciciosBinding
import com.jerson.gymapp.model.Exercicio

import com.jerson.gymapp.service.FirebaseService

class EscExerciciosActivity : AppCompatActivity() {


    private lateinit var binding : ActivityEscExerciciosBinding
    private lateinit var exercicioAdapter : ExercicioAdapter
    private var exercicioList : MutableList<Exercicio> = mutableListOf()
    private lateinit var firebase : FirebaseService
    private lateinit var nomeTreino: String


    override fun onCreate(savedInstanceState: Bundle?) {
        nomeTreino = intent.extras?.getString("nomeTreino") ?: ""
        binding = ActivityEscExerciciosBinding.inflate(layoutInflater)
        firebase = FirebaseService()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val recyclerViewExercicio = binding.recyclerViewExercicios
        recyclerViewExercicio.layoutManager = LinearLayoutManager(this)
        recyclerViewExercicio.setHasFixedSize(true)
        exercicioAdapter = ExercicioAdapter(this,exercicioList)
        recyclerViewExercicio.adapter = exercicioAdapter
        getExercicio()

        binding.btnTreinoSubmit.setOnClickListener {

            val confirmedExercicio: List<String> = exercicioList
                .filter { it.selecionado }
                .map { it.nome }

            firebase.gravarListIdExercicio(nomeTreino,confirmedExercicio){
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)

                finish()
            }
        }
    }

    /**
     * Método que pega todos os exercícios disponíveis
     */

    private fun getExercicio() {
        firebase.getAllExercicios {list ->
            exercicioList.clear()
            exercicioList.addAll(list)
            exercicioAdapter.notifyDataSetChanged()
        }
    }
}
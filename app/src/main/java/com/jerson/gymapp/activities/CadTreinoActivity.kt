package com.jerson.gymapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jerson.gymapp.Utils.Util
import com.jerson.gymapp.databinding.ActivityCadTreinoBinding
import com.jerson.gymapp.model.Treino
import com.jerson.gymapp.service.FirebaseService

class CadTreinoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadTreinoBinding
    private lateinit var treino: Treino
    private lateinit var firebase : FirebaseService
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadTreinoBinding.inflate(layoutInflater)
        firebase = FirebaseService()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnTreinoSubmit.setOnClickListener {
            extraiDados()
            if(treino.nome != "" && treino.data.toString() != "" && treino.descricao != ""){
            firebase.gravarTreino(treino){
                navegarEscolherExercicios()
            }
            }

        }

    }

    private fun navegarEscolherExercicios() {
        val intent = Intent(this,EscExerciciosActivity::class.java)
        intent.putExtra("nomeTreino",treino.nome)
        startActivity(intent)
    }

    private fun extraiDados() {
        treino.nome = binding.editNome.text.toString()
        treino.data = Util.converterTextoParaTimestamp(binding.editData.text.toString())
        treino.nome = binding.editNome.text.toString()
    }
}
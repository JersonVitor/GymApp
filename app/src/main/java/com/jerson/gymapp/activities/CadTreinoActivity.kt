package com.jerson.gymapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jerson.gymapp.Utils.Util
import com.jerson.gymapp.databinding.ActivityCadTreinoBinding
import com.jerson.gymapp.model.Treino
import com.jerson.gymapp.service.FirebaseService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class CadTreinoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadTreinoBinding
    private lateinit var treino: Treino
    private lateinit var firebase : FirebaseService
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadTreinoBinding.inflate(layoutInflater)
        firebase = FirebaseService()
        treino = Treino()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnTreinoSubmit.setOnClickListener {
            extraiDados()
            if(treino.nome != "" && treino.descricao != ""){
                firebase.gravarTreino(treino){
                    navegarEscolherExercicios()
                }
            }

        }
        binding.editData.setOnClickListener{
            showDatePickerDialog()
        }

    }

    private fun navegarEscolherExercicios() {
        val intent = Intent(this,EscExerciciosActivity::class.java)
        intent.putExtra("nomeTreino",treino.nome)
        startActivity(intent)
        finish()
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dateFormatter = SimpleDateFormat.getDateInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
                val selectedDate = selectedDateCalendar.time
                val formattedDate = dateFormatter.format(selectedDate)
                binding.editData.setText(formattedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun extraiDados() {
        treino.nome = binding.editNome.text.toString()
        treino.data = Util.converterTextoParaTimestamp(binding.editData.text.toString())
        treino.descricao = binding.editDescricao.text.toString()
    }
}
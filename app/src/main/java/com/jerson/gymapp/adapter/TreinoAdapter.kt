package com.jerson.gymapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jerson.gymapp.activities.TreinoActivity
import com.jerson.gymapp.databinding.ExercicioitemBinding
import com.jerson.gymapp.databinding.TreinoitemBinding
import com.jerson.gymapp.model.Exercicio
import com.jerson.gymapp.model.Treino
import java.text.SimpleDateFormat
import java.util.Locale

class TreinoAdapter(private val context: Context,private val treinoList: MutableList<Treino>):
    RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val listItem = TreinoitemBinding.inflate(LayoutInflater.from(context),parent,false)
        return TreinoViewHolder(listItem)
    }

    override fun getItemCount() = treinoList.size

    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val exercicio = treinoList[position]
        holder.bind(exercicio)

    }

    inner class TreinoViewHolder(binding: TreinoitemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nomeExercicio = binding.textNome
        val horario = binding.textHorario

        fun bind(treino: Treino) {

            nomeExercicio.text = treino.nome
            horario.text = SimpleDateFormat("dd/MM", Locale.getDefault()).format(treino.data.toDate().toString())
            itemView.setOnClickListener{
                val intent = Intent(context,TreinoActivity::class.java)
                intent.putExtra("treinoNome",nomeExercicio.text.toString())
                context.startActivity(intent)

            }
        }
    }
}
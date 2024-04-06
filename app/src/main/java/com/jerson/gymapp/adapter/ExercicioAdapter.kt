package com.jerson.gymapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jerson.gymapp.databinding.ExercicioitemBinding
import com.jerson.gymapp.model.Exercicio

class ExercicioAdapter(private val context: Context,private val exercicioList: MutableList<Exercicio>):
    RecyclerView.Adapter<ExercicioAdapter.ExercicioViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercicioViewHolder {
        val listItem = ExercicioitemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ExercicioViewHolder(listItem)
    }

    override fun getItemCount() = exercicioList.size

    override fun onBindViewHolder(holder: ExercicioViewHolder, position: Int) {
        val exercicio = exercicioList[position]
        holder.bind(exercicio)

    }

    inner class ExercicioViewHolder(binding: ExercicioitemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nomeExercicio = binding.textNome
        val descricaoExrcicio = binding.textDescricao
        val imgExercicio = binding.imageExercicio
        val checkItem = binding.cardExercicio

        fun bind(exercicio: Exercicio) {
            nomeExercicio.text = exercicio.nome
            descricaoExrcicio.text = exercicio.descricao
            imgExercicio.setImageURI(exercicio.imagem)

            itemView.isSelected = exercicio.selecionado
            checkItem.isSelected = exercicio.selecionado
            itemView.setOnClickListener{
                exercicio.selecionado = !exercicio.selecionado
                itemView.isSelected = exercicio.selecionado
                checkItem.isSelected = exercicio.selecionado
            }
        }
    }
}
package com.jerson.gymapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jerson.gymapp.activities.TreinoActivity
import com.jerson.gymapp.databinding.TreinoitemBinding
import com.jerson.gymapp.model.Treino
import java.text.SimpleDateFormat
import java.util.Locale

class TreinoAdapter(private val context: Context, private val treinoList: List<Treino>):
    RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val listItem = TreinoitemBinding.inflate(LayoutInflater.from(context),parent,false)
        return TreinoViewHolder(listItem)
    }

    override fun getItemCount() = treinoList.size

    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val treino = treinoList[position]
        holder.bind(treino)

    }

    inner class TreinoViewHolder(binding: TreinoitemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nomeTreino = binding.textNome
        val horario = binding.textHorario

        fun bind(treino: Treino) {
            nomeTreino.text = treino.nome
            horario.text = SimpleDateFormat("dd/MM", Locale.getDefault()).format(treino.data.toDate().toString())
            itemView.setOnClickListener{
                val intent = Intent(context,TreinoActivity::class.java)
                intent.putExtra("treinoNome",nomeTreino.text.toString())
                context.startActivity(intent)

            }
        }
    }
}
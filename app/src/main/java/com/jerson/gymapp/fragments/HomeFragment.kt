package com.jerson.gymapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jerson.gymapp.adapter.TreinoAdapter
import com.jerson.gymapp.databinding.FragmentHomeBinding
import com.jerson.gymapp.model.Treino


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private  var treinoList : List<Treino> = listOf()
    private lateinit var binding: FragmentHomeBinding

    fun setTreinos(treinos: List<Treino>){
        treinoList = treinos
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = context?.let { TreinoAdapter(it, treinoList) }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
         binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }


}
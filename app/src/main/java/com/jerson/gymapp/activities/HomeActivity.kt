package com.jerson.gymapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.jerson.gymapp.R
import com.jerson.gymapp.Utils.TreinosListener
import com.jerson.gymapp.databinding.ActivityHomeBinding
import com.jerson.gymapp.databinding.FragmentHomeBinding
import com.jerson.gymapp.fragments.CriarTreinoFragment
import com.jerson.gymapp.fragments.HomeFragment
import com.jerson.gymapp.fragments.PerfilFragment
import com.jerson.gymapp.model.Treino
import com.jerson.gymapp.service.FirebaseService


class HomeActivity : AppCompatActivity(), TreinosListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebase: FirebaseService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebase = FirebaseService()

        firebase.getAllTreinos(){ treinoList, e ->
            if (e == null){
                treinoListSucess(treinoList)
            }else{
                treinolistFailure(e)
            }
        }

        binding.bottonNavigatorBar.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.criar_treino ->  replaceFragment(CriarTreinoFragment())
                R.id.perfil -> replaceFragment(PerfilFragment())
                else -> Log.d("navigatorBar","Erro em fragment")
            }
            true
        }



    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransicion = fragmentManager.beginTransaction()
        fragmentTransicion.replace(binding.frameLayout.id,fragment)
        fragmentTransicion.commit()
    }

    override fun treinoListSucess(treinoList: List<Treino>) {
        replaceFragment(HomeFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("treinoList", ArrayList(treinoList))
            }
        })
    }

    override fun treinolistFailure(error: Exception) {
        Log.d("Exception",error.message.toString())
    }


}
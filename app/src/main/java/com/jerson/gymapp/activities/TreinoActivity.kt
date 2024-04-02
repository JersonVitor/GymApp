package com.jerson.gymapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jerson.gymapp.R
import com.jerson.gymapp.databinding.ActivityApresentacaoBinding
import com.jerson.gymapp.databinding.ActivityTreinoBinding

class TreinoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTreinoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTreinoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}
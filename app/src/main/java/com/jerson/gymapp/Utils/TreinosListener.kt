package com.jerson.gymapp.Utils

import androidx.fragment.app.Fragment
import com.jerson.gymapp.model.Treino

interface TreinosListener {
    fun treinoListSucess(treinoList: List<Treino>)
    fun treinolistFailure(error: Exception)
}
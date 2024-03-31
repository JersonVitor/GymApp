package com.jerson.gymapp.Utils

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

object Util {


    fun converterTextoParaTimestamp(textoData: String): Timestamp {
        val formatoData = SimpleDateFormat.getDateInstance()
            val data = formatoData.parse(textoData)
            return Timestamp(data!!)

    }
}
package com.jerson.gymapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp


data class Treino(
    var nome: String,
    var data: Timestamp,
    var descricao: String,
)

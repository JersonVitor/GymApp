package com.jerson.gymapp.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Exercicio(var nome: String,
                var descricao: String,
                var imagem: String?,
                var selecionado: Boolean
)
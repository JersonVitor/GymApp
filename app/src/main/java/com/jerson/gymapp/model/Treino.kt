package com.jerson.gymapp.model



import com.google.firebase.Timestamp
import java.util.Date


data class Treino(
    var nome: String,
    var data: Timestamp,
    var descricao: String,
) {
    constructor() : this("", Timestamp(Date()),"")
}


package com.jerson.gymapp.model



import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.jerson.gymapp.Utils.Util
import java.util.Date


data class Treino(
    var nome: String,
    var data: Timestamp,
    var descricao: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        Util.converterTextoParaTimestamp(parcel.readString()!!),
        parcel.readString()!!
    ) {
    }

    constructor() : this("", Timestamp(Date()),"")

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(nome)
        dest.writeString(data.toDate().toString())
        dest.writeString(descricao)
    }

    companion object CREATOR : Parcelable.Creator<Treino> {
        override fun createFromParcel(parcel: Parcel): Treino {
            return Treino(parcel)
        }

        override fun newArray(size: Int): Array<Treino?> {
            return arrayOfNulls(size)
        }
    }
}


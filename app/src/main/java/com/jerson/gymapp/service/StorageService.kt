package com.jerson.gymapp.service


import android.net.Uri
import android.util.Log
import android.view.View
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.component1
import com.google.firebase.storage.component2
import com.jerson.gymapp.model.Exercicio

object StorageService {
    private lateinit var storage : StorageReference
    private fun initStorage(caminho: String) {
        storage = FirebaseStorage.getInstance().getReference(Network.pathStorage.toString()).child(caminho)
    }

    fun uploadImage(exercicio: Exercicio,callback: (sucess: Boolean,progress: Int) -> Unit){

        val uriImage = exercicio.imagem
        initStorage("images/${exercicio.nome}.jpg")
        val uploadTask = storage.putFile(uriImage!!)
        uploadTask.addOnProgressListener {(bytesTransferred, totalByteCount) ->
            val progress = (100 * bytesTransferred) / totalByteCount
            callback(false,progress.toInt())
            Log.d("storage", "Upload is $progress% done")
        }.addOnCompleteListener{
            callback(true,100)
        }.addOnFailureListener{
            callback(false,0)
            Log.d("storage", it.message.toString())
        }

    }

    fun downloadImage(exercicio: Exercicio,callback: (uri: Uri) -> Unit)  {

        val uriImage = exercicio.imagem
        initStorage("images/${exercicio.nome}.jpg")
        var uploadTask = storage.downloadUrl
            .addOnCompleteListener { uri ->
                callback(
                    uri.result
                )
            }.addOnFailureListener{

            }
    }
    fun exceptionStorage(view: View){

    }
}
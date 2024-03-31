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
    private fun initStorage(caminho: String){
        storage = FirebaseStorage.getInstance(Network.pathStorage.name).reference.child(caminho)
    }


    fun uploadImage(exercicio: Exercicio,callback: (progress: Int) -> Unit) : Boolean{
        var resp = false
        val uriImage = Uri.parse(exercicio.imagem)
        initStorage("images/${exercicio.nome}.jpg")
        val uploadTask = storage.putFile(uriImage)
        uploadTask.addOnProgressListener {(bytesTransferred, totalByteCount) ->
            val progress = (100 * bytesTransferred) / totalByteCount
            callback(progress.toInt())
            Log.d("storage", "Upload is $progress% done")
        }.addOnCompleteListener{
            resp = true
        }.addOnFailureListener{
            //TODO função de exception
        }
        return resp
    }

    fun downloadImage(exercicio: Exercicio,callback: (uri: Uri) -> Unit)  {

        val uriImage = Uri.parse(exercicio.imagem)
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
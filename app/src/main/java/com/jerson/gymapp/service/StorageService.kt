package com.jerson.gymapp.service

import android.net.Uri
import android.util.Log
import android.view.View
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jerson.gymapp.model.Exercicio

object StorageService {
    // Referência para o serviço de armazenamento do Firebase
    private lateinit var storage: StorageReference

    /**
     * Inicializa o serviço de armazenamento com o caminho especificado.
     *
     * @param caminho Caminho para o armazenamento.
     */
    private fun initStorage(caminho: String) {
        storage = FirebaseStorage.getInstance().getReference(Network.pathStorage.toString()).child(caminho)
    }

    /**
     * Realiza o upload de uma imagem para o armazenamento.
     *
     * @param exercicio Objeto Exercicio contendo a imagem a ser carregada.
     * @param callback Callback a ser chamado após o upload, informando sucesso e progresso.
     */
    fun uploadImage(exercicio: Exercicio, callback: (success: Boolean, progress: Int) -> Unit) {
        val uriImage = exercicio.imagem
        initStorage("images/${exercicio.nome}.jpg")
        val uploadTask = storage.putFile(uriImage!!)
        uploadTask.addOnProgressListener {
                val progress = (100 * it.bytesTransferred) / it.totalByteCount
                callback(false, progress.toInt())
                Log.d("storage", "Upload is $progress% done")
        }.addOnCompleteListener {
            callback(true, 100)
        }.addOnFailureListener {
            callback(false, 0)
            Log.d("storage", it.message.toString())
        }
    }

    /**
     * Realiza o download de uma imagem do armazenamento.
     *
     * @param exercicio Objeto Exercicio contendo  a ser baixada.
     * @param callback Callback a ser chamado após o download, passando a URI da imagem.
     */
    fun downloadImage(nome:String, callback: (uri: Uri) -> Unit) {
        initStorage("images/${nome}.jpg")
        var uploadTask = storage.downloadUrl
            .addOnCompleteListener { uri ->
                callback(uri.result)
            }.addOnFailureListener {
                // Tratamento de falha
            }
    }

}

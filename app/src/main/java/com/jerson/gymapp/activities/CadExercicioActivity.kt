package com.jerson.gymapp.activities


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jerson.gymapp.databinding.ActivityCadExercicioBinding
import com.jerson.gymapp.model.Exercicio
import com.jerson.gymapp.service.FirebaseService
import com.jerson.gymapp.service.StorageService


class CadExercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadExercicioBinding
    private lateinit var dialog: AlertDialog
    private var exercicio = Exercicio()
    private lateinit var firebase: FirebaseService


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCadExercicioBinding.inflate(layoutInflater)
        firebase = FirebaseService()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // Configuração do botão para submeter o exercício
        binding.btnExercicioSubmit.setOnClickListener {view ->
            exercicio.nome = binding.editNome.text.toString()
            exercicio.descricao = binding.editDescricao.text.toString()
            firebase.gravarExercicio(exercicio){
                //Envio da imagem
                StorageService.uploadImage(exercicio) { confirm, progress ->
                    binding.progressBar.progress = progress
                    if (confirm) {
                        telaHome()
                    } else {
                        val snackbar = Snackbar.make(
                            view,
                            "não foi possível enviar a imagem",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }

                }

            }


        }
        binding.btnLoadImage.setOnClickListener {
            verificadorPermissaoGaleria()
        }

    }

    /**
     * Navega para a tela principal.
     */
    private fun telaHome(){
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object {
        private const val PERMISSAO_GALERIA = Manifest.permission.READ_EXTERNAL_STORAGE
    }

    private val requestGaleria =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){permissao ->
            if(permissao){
                resultGaleria.launch(Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
            }else{
                dialogPermissao()
            }

        }

    private val resultGaleria =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK && result.data != null){

                binding.imageExercicio.setImageURI(null)
                binding.imageExercicio.setImageURI(result.data?.data)
                exercicio.imagem = result.data?.data
            }
        }

    private fun vefificadorPermissao(permissao: String): Boolean {
        return ContextCompat.checkSelfPermission(this,permissao) == PackageManager.PERMISSION_GRANTED
    }
    private fun verificadorPermissaoGaleria(){
        val permissaoAceita = vefificadorPermissao(PERMISSAO_GALERIA)

        when{
            permissaoAceita -> {
                resultGaleria.launch(Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
            }
            shouldShowRequestPermissionRationale(PERMISSAO_GALERIA) -> dialogPermissao()
            else ->requestGaleria.launch(PERMISSAO_GALERIA)
        }
    }
    /**
     * Exibe o diálogo para solicitar permissão de acesso à galeria.
     */
    private fun dialogPermissao(){
        val builder = AlertDialog.Builder(this)
            .setTitle("Permissão a Galeria")
            .setMessage("Precisamos do acesso a galeria, gostaria de permitir agora? ")
            .setNegativeButton("Não") {_, _ ->
                dialog.dismiss()
            }.setPositiveButton("Sim"){ _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package",packageName,null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                dialog.dismiss()
            }
        dialog = builder.create()
        builder.show()
    }



}
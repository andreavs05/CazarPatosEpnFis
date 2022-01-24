package com.cachipuendoandrea.cazarpatos

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.io.File

class LoginActivity : AppCompatActivity() {
    lateinit var manejarArchivos: FileHandler
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword:EditText
    lateinit var buttonLogin: Button
    lateinit var buttonNewUser:Button
    lateinit var checkBoxRecordarme: CheckBox
    lateinit var mediaPlayer: MediaPlayer

    //_______________________________________CONSTRUCTOR__________________________________________//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //INICIALIZACIÓN DE VARIABLES
        manejarArchivos = SharedPreferencesManager(this)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonNewUser = findViewById(R.id.buttonNewUser)
        checkBoxRecordarme = findViewById(R.id.checkBoxRecordarme)

        //_____________________________________________//
        LeerDatosDePreferencias()

        //_________      EVENTOS CLIC        __________//

        //PARA EL LOGIN
        buttonLogin.setOnClickListener{
            val email = editTextEmail.text.toString()
            val clave = editTextPassword.text.toString()

            //Validaciones de los datos requeridos y formatos
            if(!ValidarDatosRequeridos())
                return@setOnClickListener
            //Guardar Datos de Preferencias
            GuardarDatosPreferencias()

            //Si pasas validación de datos requeridos, ir a la pantalla principal
            val intencion = Intent(this, MainActivity::class.java)
            intencion.putExtra(EXTRA_LOGIN, email)
            startActivity(intencion)
        }

        //PARA NUEVOS USUARIOS
        buttonNewUser.setOnClickListener{

        }
        mediaPlayer = MediaPlayer.create(this, R.raw.title_screen)
        mediaPlayer.start()

    }



    //_______________________________________ FUNCIONES __________________________________________//
    private fun LeerDatosDePreferencias() {
        val listadoLeido = manejarArchivos.ReadInformation()
        if(listadoLeido.first != null){
            checkBoxRecordarme.isChecked = true
        }
        editTextEmail.setText(listadoLeido.first)
        editTextPassword.setText(listadoLeido.second)
    }

    private fun GuardarDatosPreferencias(){
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()
        val listadoAGrabar:Pair<String,String>

        if(checkBoxRecordarme.isChecked){
            listadoAGrabar = email to clave
        }else{
            listadoAGrabar = "" to ""
        }
        manejarArchivos.SaveInformation(listadoAGrabar)
     }

    private fun ValidarDatosRequeridos():Boolean{
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()

        if(email.isEmpty()){
            editTextEmail.setError("El email es obligatorio")
            editTextEmail.requestFocus()
            return false
        }
        if(clave.isEmpty()){
            editTextPassword.setError("La clave es obligatorio")
            editTextPassword.requestFocus()
            return false
        }
        if(clave.length<3){
            editTextPassword.setError("La clave debe tener al menos 3 caracteres")
            editTextPassword.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }




}
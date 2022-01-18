package com.cachipuendoandrea.cazarpatos

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var textViewUsuario: TextView
    lateinit var textViewContador: TextView
    lateinit var textViewTiempo: TextView
    lateinit var imageViewPato: ImageView
    var contador = 0
    var anchoPantalla = 0
    var alturaPantalla = 0
    var gameOver = false

    //_______________________________________CONSTRUCTOR__________________________________________//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializamos las variables
        textViewUsuario = findViewById(R.id.textViewUsuario)
        textViewContador = findViewById(R.id.textViewContador)
        textViewTiempo = findViewById(R.id.textViewTiempo)
        imageViewPato = findViewById(R.id.imageViewPato)

        //Obtener el usuario de la pantalla login
        val extras = intent.extras ?: return
        val usuario = extras.getString(EXTRA_LOGIN) ?: "Unknow"
        textViewUsuario.setText(usuario)

        //Determina el ancho y largo de pantalla
        inicializarPantalla()

        //Cuenta regresiva del juego
        iniciarCuentaRegresiva()

        //Evento clic para la imagen del pato
        imageViewPato.setOnClickListener{
            if (gameOver) return@setOnClickListener
            contador++
            MediaPlayer.create(this, R.raw.gunshot).start()
            textViewContador.setText(contador.toString())
            imageViewPato.setImageResource(R.drawable.duck_clicked)

            //Evento que se ejecuta luego de 500 milisegundos
            Handler().postDelayed(Runnable {
                imageViewPato.setImageResource(R.drawable.duck)
                moverPato()
            }, 500)

        }
    }

    //_______________________________________FUNCIONES__________________________________________//

    //      Para tomar las medidas de la pantalla

    private fun inicializarPantalla(){
        //1. Obtenemos el tamaño de la pantalla del dispositivo
        val display = this.resources.displayMetrics
        anchoPantalla = display.widthPixels
        alturaPantalla = display.heightPixels

    }

    //       Para mover el pato

    private fun moverPato(){
        val min = imageViewPato.getWidth()/2
        val maximoX = anchoPantalla - imageViewPato.getWidth()
        val minimoY = alturaPantalla - imageViewPato.getHeight()

        //Generamos dos números aleatorios para las coordenadas de X , Y
        val randomX = Random().nextInt(maximoX - min) + 1
        val randomY = Random().nextInt(minimoY - min) + 1

        imageViewPato.setX(randomX.toFloat())
        imageViewPato.setY(randomY.toFloat())
    }

    //        Para cuenta regresiva

    private fun iniciarCuentaRegresiva(){
        object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val segundosRestantes = millisUntilFinished / 1000
                textViewTiempo.setText("${segundosRestantes}")
            }

            override fun onFinish() {
                textViewTiempo.setText("0s")
                gameOver=true
                mostrarDialogoGameOver()
            }
        }.start()
    }

    //          MOstrar Dialogo

    private fun mostrarDialogoGameOver(){
        val builder = AlertDialog.Builder(this)
        builder
            .setMessage("\"Felicidades!!\\nHas conseguido cazar $contador patos\"")
            .setTitle("Fin del Juego")
            .setPositiveButton("Reiniciar",
            { _, _ ->
                contador = 0
                gameOver = false
                textViewContador.setText(contador.toString())
                moverPato()
                iniciarCuentaRegresiva()
            })

            .setNegativeButton("Cerrar" , { _,_->
                //Dialogo.dismiss()
            })
        builder.create().show()
    }

}
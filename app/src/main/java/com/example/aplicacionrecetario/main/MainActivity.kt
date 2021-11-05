package com.example.aplicacionrecetario.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.entidades.RecetaAperitivo
import com.example.aplicacionrecetario.entidades.RecetaEnsalada
import com.example.aplicacionrecetario.entidades.RecetaPlatoPrincipal
import com.example.aplicacionrecetario.entidades.RecetaPostre
import com.example.aplicacionrecetario.vista.menurecetas.VistaRecetaAperitivo
import com.example.aplicacionrecetario.vista.menurecetas.VistaRecetaEnsalada
import com.example.aplicacionrecetario.vista.menurecetas.VistaRecetaPlatoPrincipal
import com.example.aplicacionrecetario.vista.menurecetas.VistaRecetaPostre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonAperitivos =  findViewById<ImageButton>(R.id.botonAperitivos)
        botonAperitivos.setOnClickListener {
            val intento1 = Intent(this, VistaRecetaAperitivo::class.java)
            startActivity(intento1)
        }

        val botonEnsalada =  findViewById<ImageButton>(R.id.botonEnsalada)
        botonEnsalada.setOnClickListener {
            val intento1 = Intent(this, VistaRecetaEnsalada::class.java)
            startActivity(intento1)
        }

        val botonPlatoPrincipal =  findViewById<ImageButton>(R.id.botonPlatoPrincipal)
        botonPlatoPrincipal .setOnClickListener {
            val intento1 = Intent(this, VistaRecetaPlatoPrincipal::class.java)
            startActivity(intento1)
        }

        val botonPostre =  findViewById<ImageButton>(R.id.botonPostre)
        botonPostre.setOnClickListener {
            val intento1 = Intent(this, VistaRecetaPostre::class.java)
            startActivity(intento1)
        }
    }
}
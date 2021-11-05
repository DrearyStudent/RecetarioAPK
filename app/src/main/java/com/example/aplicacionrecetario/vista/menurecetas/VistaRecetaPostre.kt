package com.example.aplicacionrecetario.vista.menurecetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.adaptador.RecetaPostreAdaptador
import com.example.aplicacionrecetario.entidades.RecetaPostre
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaPostre
import com.example.aplicacionrecetario.vista.detallesrecetas.DetallesRecetaPostres
import com.example.aplicacionrecetario.vista.webview.VistaWebviewRecetaPostre
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_vista_receta_postre.*

class VistaRecetaPostre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_receta_postre)

        val botonAccionInternetPostre = findViewById<FloatingActionButton>(R.id.botonAccionInternetPostre)
        botonAccionInternetPostre.setOnClickListener {
            val intento1 = Intent(this, VistaWebviewRecetaPostre::class.java)
            startActivity(intento1)
        }

        // Abrir ventana agregar
        val botonAccionAgregarRecetaPostre = findViewById<FloatingActionButton>(R.id.botonAccionAgregarPostre)
        botonAccionAgregarRecetaPostre.setOnClickListener {
            val intento1 = Intent(this, VistaAgregarRecetaPostre::class.java)
            startActivity(intento1)
        }

        // Mostrar la lista en la pantalla
        var listaRecetaPostre = emptyList<RecetaPostre>()
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        baseDeDatos.recetasPostre().obtenerTodos().observe(this, Observer {
            listaRecetaPostre = it
            val adaptador = RecetaPostreAdaptador(this, listaRecetaPostre)
            listaPostres.adapter = adaptador
        })

        // Mostrar detalles al dar clic en un elemento de la lista
        listaPostres.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetallesRecetaPostres::class.java)
            intent.putExtra("idRecetaPostre", listaRecetaPostre[position].idRecetaPostre)
            startActivity(intent)
        }
    }
}
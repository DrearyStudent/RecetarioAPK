package com.example.aplicacionrecetario.vista.menurecetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.aplicacionrecetario.vista.detallesrecetas.DetallesRecetaAperitivos
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.entidades.RecetaAperitivo
import com.example.aplicacionrecetario.adaptador.RecetaAperitivoAdaptador
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaAperitivo
import com.example.aplicacionrecetario.vista.webview.VistaWebviewRecetaAperitivo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_vista_receta_aperitivo.*

class VistaRecetaAperitivo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_receta_aperitivo)

        val botonAccionInternetAperitivo = findViewById<FloatingActionButton>(R.id.botonAccionInternetAperitivo)
        botonAccionInternetAperitivo.setOnClickListener {
            val intento1 = Intent(this, VistaWebviewRecetaAperitivo::class.java)
            startActivity(intento1)
        }

        // Abrir ventana agregar
        val botonAccionAgregarRecetaAperitivo = findViewById<FloatingActionButton>(R.id.botonAccionAgregarAperitivo)
        botonAccionAgregarRecetaAperitivo.setOnClickListener {
            val intento1 = Intent(this, VistaAgregarRecetaAperitivo::class.java)
            startActivity(intento1)
        }

        // Mostrar la lista en la pantalla
        var listaRecetaAperitivo = emptyList<RecetaAperitivo>()
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        baseDeDatos.recetasAperitivo().obtenerTodos().observe(this, Observer {
            listaRecetaAperitivo = it
            val adaptador = RecetaAperitivoAdaptador(this, listaRecetaAperitivo)
            listaAperitivos.adapter = adaptador
        })

        // Mostrar detalles al dar clic en un elemento de la lista
        listaAperitivos.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetallesRecetaAperitivos::class.java)
            intent.putExtra("idRecetaAperitivo", listaRecetaAperitivo[position].idRecetaAperitivo)
            startActivity(intent)
        }
    }
}
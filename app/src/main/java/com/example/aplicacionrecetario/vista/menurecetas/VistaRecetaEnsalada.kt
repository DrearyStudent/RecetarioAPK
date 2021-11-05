package com.example.aplicacionrecetario.vista.menurecetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.adaptador.RecetaEnsaladaAdaptador
import com.example.aplicacionrecetario.entidades.RecetaEnsalada
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaEnsalada
import com.example.aplicacionrecetario.vista.detallesrecetas.DetallesRecetaEnsaladas
import com.example.aplicacionrecetario.vista.webview.VistaWebviewRecetaEnsalada
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_vista_receta_ensalada.*

class VistaRecetaEnsalada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_receta_ensalada)

        val botonAccionInternetEnsalada = findViewById<FloatingActionButton>(R.id.botonAccionInternetEnsalada)
        botonAccionInternetEnsalada.setOnClickListener {
            val intento1 = Intent(this, VistaWebviewRecetaEnsalada::class.java)
            startActivity(intento1)
        }

        // Abrir ventana agregar
        val botonAccionAgregarRecetaEnsalada = findViewById<FloatingActionButton>(R.id.botonAccionAgregarEnsalada)
        botonAccionAgregarRecetaEnsalada.setOnClickListener {
            val intento1 = Intent(this, VistaAgregarRecetaEnsalada::class.java)
            startActivity(intento1)
        }

        // Mostrar la lista en la pantalla
        var listaRecetaEnsalada = emptyList<RecetaEnsalada>()
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        baseDeDatos.recetasEnsalada().obtenerTodos().observe(this, Observer {
            listaRecetaEnsalada = it
            val adaptador = RecetaEnsaladaAdaptador(this, listaRecetaEnsalada)
            listaEnsaladas.adapter = adaptador
        })

        // Mostrar detalles al dar clic en un elemento de la lista
        listaEnsaladas.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetallesRecetaEnsaladas::class.java)
            intent.putExtra("idRecetaEnsalada", listaRecetaEnsalada[position].idRecetaEnsalada)
            startActivity(intent)
        }
    }
}
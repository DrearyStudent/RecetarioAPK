package com.example.aplicacionrecetario.vista.menurecetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.adaptador.RecetaPlatoPrincipalAdaptador
import com.example.aplicacionrecetario.entidades.RecetaPlatoPrincipal
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaPlatoPrincipal
import com.example.aplicacionrecetario.vista.detallesrecetas.DetallesRecetaPlatoPrincipal
import com.example.aplicacionrecetario.vista.webview.VistaWebviewRecetaPlatoPrincipal
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_vista_receta_plato_principal.*

class VistaRecetaPlatoPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_receta_plato_principal)

        val botonAccionInternetPlatoPrincipal = findViewById<FloatingActionButton>(R.id.botonAccionInternetPlatoPrincipal)
        botonAccionInternetPlatoPrincipal.setOnClickListener {
            val intento1 = Intent(this, VistaWebviewRecetaPlatoPrincipal::class.java)
            startActivity(intento1)
        }

        // Abrir ventana agregar
        val botonAccionAgregarRecetaPlatoPrincipal = findViewById<FloatingActionButton>(R.id.botonAccionAgregarPlatoPrincipal)
        botonAccionAgregarRecetaPlatoPrincipal.setOnClickListener {
            val intento1 = Intent(this, VistaAgregarRecetaPlatoPrincipal::class.java)
            startActivity(intento1)
        }

        // Mostrar la lista en la pantalla
        var listaRecetaPlatoPrincipal = emptyList<RecetaPlatoPrincipal>()
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        baseDeDatos.recetasPlatoPrincipal().obtenerTodos().observe(this, Observer {
            listaRecetaPlatoPrincipal = it
            val adaptador = RecetaPlatoPrincipalAdaptador(this, listaRecetaPlatoPrincipal)
            listaPlatosPrincipales.adapter = adaptador
        })

        // Mostrar detalles al dar clic en un elemento de la lista
        listaPlatosPrincipales.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetallesRecetaPlatoPrincipal::class.java)
            intent.putExtra("idRecetaPlatoPrincipal", listaRecetaPlatoPrincipal[position].idRecetaPlatoPrincipal)
            startActivity(intent)
        }
    }
}
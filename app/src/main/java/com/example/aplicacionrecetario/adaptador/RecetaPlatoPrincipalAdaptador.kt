package com.example.aplicacionrecetario.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.entidades.RecetaPlatoPrincipal
import kotlinx.android.synthetic.main.lista_receta_plato_principal.view.*

class RecetaPlatoPrincipalAdaptador(private val mContext: Context, private val listaRecetasPlatoPrincipal:List<RecetaPlatoPrincipal>):
    ArrayAdapter<RecetaPlatoPrincipal>(mContext, 0, listaRecetasPlatoPrincipal) {

    override  fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_receta_plato_principal, parent, false)
        val recetaPlatoPrincipal = listaRecetasPlatoPrincipal[position]
        layout.nombrePlatoPrincipal.text = recetaPlatoPrincipal.nombre
        layout.imagenPlatoPrincipal.setImageResource(recetaPlatoPrincipal.imagen)
        return layout
    }

}
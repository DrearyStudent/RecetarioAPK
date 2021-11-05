package com.example.aplicacionrecetario.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.entidades.RecetaPostre
import kotlinx.android.synthetic.main.lista_receta_postre.view.*

class RecetaPostreAdaptador(private val mContext: Context, private val listaRecetasPostres:List<RecetaPostre>):
    ArrayAdapter<RecetaPostre>(mContext, 0, listaRecetasPostres) {

    override  fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_receta_postre, parent, false)
        val recetaPostre = listaRecetasPostres[position]
        layout.nombrePostre.text = recetaPostre.nombre
        layout.imagenPostre.setImageResource(recetaPostre.imagen)
        return layout
    }

}
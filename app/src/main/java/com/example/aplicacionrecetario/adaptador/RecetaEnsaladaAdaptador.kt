package com.example.aplicacionrecetario.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.entidades.RecetaEnsalada
import kotlinx.android.synthetic.main.lista_receta_ensalada.view.*

class RecetaEnsaladaAdaptador(private val mContext: Context, private val listaRecetasEnsaladas:List<RecetaEnsalada>):
    ArrayAdapter<RecetaEnsalada>(mContext, 0, listaRecetasEnsaladas) {

    override  fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_receta_ensalada, parent, false)
        val recetaEnsalada = listaRecetasEnsaladas[position]
        layout.nombreEnsalada.text = recetaEnsalada.nombre
        layout.imagenEnsalada.setImageResource(recetaEnsalada.imagen)
        return layout
    }

}
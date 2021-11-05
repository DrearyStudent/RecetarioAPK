package com.example.aplicacionrecetario.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.entidades.RecetaAperitivo
import kotlinx.android.synthetic.main.lista_receta_aperitivo.view.*

class RecetaAperitivoAdaptador(private val mContext: Context, private val listaRecetasAperitivos:List<RecetaAperitivo>):
    ArrayAdapter<RecetaAperitivo>(mContext, 0, listaRecetasAperitivos) {

        override  fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_receta_aperitivo, parent, false)
            val recetaAperitivo = listaRecetasAperitivos[position]
            layout.nombreAperitivo.text = recetaAperitivo.nombre
            layout.imagenAperitivo.setImageResource(recetaAperitivo.imagen)
            return layout
        }

}
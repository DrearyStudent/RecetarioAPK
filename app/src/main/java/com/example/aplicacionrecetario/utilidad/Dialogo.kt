package com.example.aplicacionrecetario.utilidad

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Context


class Dialogo {
    companion object {
        var respuesta = false
        fun mostrarDialogoConfirmaci√≥n(contexto: Context, mensaje: CharSequence, titulo: CharSequence) {
            AlertDialog.Builder(contexto).apply {
                setTitle(titulo)
                setMessage(mensaje)
                setPositiveButton("S√≠", DialogInterface.OnClickListener { dialog, id ->
                    respuesta = true
                    dialog.cancel()
                }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    respuesta = false
                    dialog.cancel()
                })
            }.show()
        }
    }
}
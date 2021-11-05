package com.example.aplicacionrecetario.vista.agregarrecetas

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.entidades.RecetaPostre
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_aperitivo.*
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_ensalada.*
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_postre.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class VistaAgregarRecetaPostre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_agregar_receta_postre)
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        var idRecetaPostre: Int? = null

        // Para mostrar los campos a actualizar
        if (intent.hasExtra("postre")) {
            val postre = intent.extras?.getSerializable("postre") as RecetaPostre
            nombreRecetaPostreTxt.setText(postre.nombre)
            ingredientesRecetaPostreTxt.setText(postre.ingredientes)
            instruccionRecetaPostreTxt.setText(postre.instrucciones)
            idRecetaPostre = postre.idRecetaPostre
        }

        botonGuardarRecetaPostre.setOnClickListener {
            if (!estanCamposVacios()) {
                //Mostrar diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea guardar la receta con estos datos?")
                    //Acción positivo
                    setPositiveButton("Sí") { dialogInterface: DialogInterface, i:Int ->
                        val nombre = nombreRecetaPostreTxt.text.toString()
                        val ingredientes = ingredientesRecetaPostreTxt.text.toString()
                        val instrucciones = instruccionRecetaPostreTxt.text.toString()
                        val postre = RecetaPostre(nombre, ingredientes, instrucciones, R.drawable.postre2)
                        //Saber si actualiza o elimina
                        if (idRecetaPostre != null) {
                            // Para actualizarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    postre.idRecetaPostre = idRecetaPostre
                                    baseDeDatos.recetasPostre().actualizarRecetaPostre(postre)
                                    this@VistaAgregarRecetaPostre.finish()
                                }
                            } catch (e: SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaPostre, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Para registrarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    baseDeDatos.recetasPostre().insertarRecetaPostre(postre)
                                    this@VistaAgregarRecetaPostre.finish()
                                }
                            } catch (e:SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaPostre, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    //Acción negativo
                    setNegativeButton("No",null)
                }.show()
            } else {
                Toast.makeText(this, "Debe llenar todos los campos para continuar...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun estanCamposVacios(): Boolean {
        return nombreRecetaPostreTxt.getText().trim().isEmpty() ||
                ingredientesRecetaPostreTxt.getText().trim().isEmpty() ||
                instruccionRecetaPostreTxt.getText().trim().isEmpty()
    }
}
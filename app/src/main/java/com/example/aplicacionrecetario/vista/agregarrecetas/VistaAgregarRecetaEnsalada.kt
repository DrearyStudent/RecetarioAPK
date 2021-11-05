package com.example.aplicacionrecetario.vista.agregarrecetas

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.entidades.RecetaEnsalada
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_aperitivo.*
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_ensalada.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class VistaAgregarRecetaEnsalada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_agregar_receta_ensalada)
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        var idRecetaEnsalada: Int? = null

        if (intent.hasExtra("ensalada")) {
            val ensalada = intent.extras?.getSerializable("ensalada") as RecetaEnsalada
            nombreRecetaEnsaladaTxt.setText(ensalada.nombre)
            ingredientesRecetaEnsaladaTxt.setText(ensalada.ingredientes)
            instruccionRecetaEnsaladaTxt.setText(ensalada.instrucciones)
            idRecetaEnsalada = ensalada.idRecetaEnsalada
        }

        botonGuardarRecetaEnsalada.setOnClickListener {
            if (!estanCamposVacios()) {
                //Mostrar diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea guardar la receta con estos datos?")
                    //Acción positivo
                    setPositiveButton("Sí") { dialogInterface: DialogInterface, i:Int ->
                        val nombre = nombreRecetaEnsaladaTxt.text.toString()
                        val ingredientes = ingredientesRecetaEnsaladaTxt.text.toString()
                        val instrucciones = instruccionRecetaEnsaladaTxt.text.toString()
                        val ensalada = RecetaEnsalada(nombre, ingredientes, instrucciones, R.drawable.ensalada2)

                        //Saber si actualiza o elimina
                        if (idRecetaEnsalada != null) {
                            // Para actualizarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    ensalada.idRecetaEnsalada = idRecetaEnsalada
                                    baseDeDatos.recetasEnsalada().actualizarRecetaEnsalada(ensalada)
                                    this@VistaAgregarRecetaEnsalada.finish()
                                }
                            } catch (e: SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaEnsalada, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Para registrarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    baseDeDatos.recetasEnsalada().insertarRecetaEnsalada(ensalada)
                                    this@VistaAgregarRecetaEnsalada.finish()
                                }
                            } catch (e:SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaEnsalada, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
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
        return nombreRecetaEnsaladaTxt.getText().trim().isEmpty() ||
                ingredientesRecetaEnsaladaTxt.getText().trim().isEmpty() ||
                instruccionRecetaEnsaladaTxt.getText().trim().isEmpty()
    }
}
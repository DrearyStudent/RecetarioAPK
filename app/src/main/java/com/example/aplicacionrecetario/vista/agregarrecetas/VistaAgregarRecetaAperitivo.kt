package com.example.aplicacionrecetario.vista.agregarrecetas

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.entidades.RecetaAperitivo
import kotlinx.android.synthetic.main.activity_detalles_receta_aperitivos.*
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_aperitivo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class VistaAgregarRecetaAperitivo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_agregar_receta_aperitivo)
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        var idRecetaAperitivo: Int? = null

        // Para mostrar los campos a actualizar
        if (intent.hasExtra("aperitivo")) {
            val aperitivo = intent.extras?.getSerializable("aperitivo") as RecetaAperitivo
            nombreRecetaAperitivoTxt.setText(aperitivo.nombre)
            ingredientesRecetaAperitivoTxt.setText(aperitivo.ingredientes)
            instruccionRecetaAperitivoTxt.setText(aperitivo.instrucciones)
            idRecetaAperitivo = aperitivo.idRecetaAperitivo
        }

        botonGuardarRecetaAperitivo.setOnClickListener {
            if (!estanCamposVacios()) {
                //Mostrar diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea guardar la receta con estos datos?")
                    //Acción positivo
                    setPositiveButton("Sí") {dialogInterface:DialogInterface, i:Int ->
                        val nombre = nombreRecetaAperitivoTxt.text.toString()
                        val ingredientes = ingredientesRecetaAperitivoTxt.text.toString()
                        val instrucciones = instruccionRecetaAperitivoTxt.text.toString()
                        val aperitivo = RecetaAperitivo(nombre, ingredientes, instrucciones, R.drawable.aperitivo2)
                        //Saber si actualiza o elimina
                        if (idRecetaAperitivo != null) {
                            // Para actualizarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    aperitivo.idRecetaAperitivo = idRecetaAperitivo
                                    baseDeDatos.recetasAperitivo().actualizarRecetaAperitivo(aperitivo)
                                    this@VistaAgregarRecetaAperitivo.finish()
                                }
                            } catch (e:SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaAperitivo, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Para registrarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    baseDeDatos.recetasAperitivo().insertarRecetaAperitivo(aperitivo)
                                    this@VistaAgregarRecetaAperitivo.finish()
                                }
                            } catch (e:SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaAperitivo, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
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
        return nombreRecetaAperitivoTxt.getText().trim().isEmpty() ||
                ingredientesRecetaAperitivoTxt.getText().trim().isEmpty() ||
                instruccionRecetaAperitivoTxt.getText().trim().isEmpty()
    }
}
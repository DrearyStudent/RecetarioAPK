package com.example.aplicacionrecetario.vista.agregarrecetas

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.entidades.RecetaPlatoPrincipal
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_aperitivo.*
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_ensalada.*
import kotlinx.android.synthetic.main.activity_vista_agregar_receta_plato_principal.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class VistaAgregarRecetaPlatoPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_agregar_receta_plato_principal)
        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        var idRecetaPlatoPrincipal: Int? = null

        // Para mostrar los campos a actualizar
        if (intent.hasExtra("platoPrincipal")) {
            val platoPrincipal = intent.extras?.getSerializable("platoPrincipal") as RecetaPlatoPrincipal
            nombreRecetaPlatoPrincipalTxt.setText(platoPrincipal.nombre)
            ingredientesRecetaPlatoPrincipalTxt.setText(platoPrincipal.ingredientes)
            instruccionRecetaPlatoPrincipalTxt.setText(platoPrincipal.instrucciones)
            idRecetaPlatoPrincipal = platoPrincipal.idRecetaPlatoPrincipal
        }

        botonGuardarRecetaPlatoPrincipal.setOnClickListener {
            if (!estanCamposVacios()) {
                //Mostrar diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea guardar la receta con estos datos?")
                    //Acción positivo
                    setPositiveButton("Sí") { dialogInterface: DialogInterface, i:Int ->
                        val nombre = nombreRecetaPlatoPrincipalTxt.text.toString()
                        val ingredientes = ingredientesRecetaPlatoPrincipalTxt.text.toString()
                        val instrucciones = instruccionRecetaPlatoPrincipalTxt.text.toString()
                        val platoPrincipal = RecetaPlatoPrincipal(nombre, ingredientes, instrucciones, R.drawable.plato_principal_2)
                        //Saber si actualiza o elimina
                        if (idRecetaPlatoPrincipal != null) {
                            // Para actualizarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    platoPrincipal.idRecetaPlatoPrincipal = idRecetaPlatoPrincipal
                                    baseDeDatos.recetasPlatoPrincipal().actualizarRecetaPlatoPrincipal(platoPrincipal)
                                    this@VistaAgregarRecetaPlatoPrincipal.finish()
                                }
                            } catch (e: SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaPlatoPrincipal, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Para registrarlo
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    baseDeDatos.recetasPlatoPrincipal().insertarRecetaPlatoPrincipal(platoPrincipal)
                                    this@VistaAgregarRecetaPlatoPrincipal.finish()
                                }
                            } catch (e:SQLException) {
                                Toast.makeText(this@VistaAgregarRecetaPlatoPrincipal, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
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
        return nombreRecetaPlatoPrincipalTxt.getText().trim().isEmpty() ||
                ingredientesRecetaPlatoPrincipalTxt.getText().trim().isEmpty() ||
                instruccionRecetaPlatoPrincipalTxt.getText().trim().isEmpty()
    }

}
package com.example.aplicacionrecetario.vista.detallesrecetas

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.aplicacionrecetario.R
import com.example.aplicacionrecetario.accesoadatos.BaseDeDatos
import com.example.aplicacionrecetario.entidades.RecetaEnsalada
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaEnsalada
import kotlinx.android.synthetic.main.activity_detalles_receta_aperitivos.*
import kotlinx.android.synthetic.main.activity_detalles_receta_ensaladas.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class DetallesRecetaEnsaladas : AppCompatActivity() {
    private lateinit var baseDeDatos: BaseDeDatos
    private lateinit var ensalada: RecetaEnsalada
    private lateinit var ensaladaLiveData: LiveData<RecetaEnsalada>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_receta_ensaladas)
        baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)

        // Ver en la lista y que se actualice por ID
        val idEnsalada = intent.getIntExtra("idRecetaEnsalada", 0)
        ensaladaLiveData = baseDeDatos.recetasEnsalada().obtenerPorId(idEnsalada)
        ensaladaLiveData.observe(this, Observer {
            ensalada = it
            nombreEnsaladaDetalles.text = ensalada.nombre
            ingredientesEnsalada.text = ensalada.ingredientes
            instruccionesEnsalada.text = ensalada.instrucciones
            imagenEnsaladaDetalles.setImageResource(ensalada.imagen)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_receta_ensalada, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Al dar clic en Editar
            R.id.itemEditarRecetaEnsalada -> {
                val intento1 = Intent(this, VistaAgregarRecetaEnsalada::class.java)
                intento1.putExtra("ensalada", ensalada)
                startActivity(intento1)
            }
            //Al dar clic en Eliminar
            R.id.itemEliminarRecetaEnsalada -> {
                //Mostrar Diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea eliminar esta receta?")
                    //Acción Positivo
                    setPositiveButton("Sí") { dialogInterface: DialogInterface, i:Int ->
                        ensaladaLiveData.removeObservers(this@DetallesRecetaEnsaladas)
                        try {
                            CoroutineScope(Dispatchers.IO).launch {
                                baseDeDatos.recetasEnsalada().borrarRecetaEnsalada(ensalada)
                                this@DetallesRecetaEnsaladas.finish()
                            }
                        } catch (e: SQLException) {
                            Toast.makeText(this@DetallesRecetaEnsaladas, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //Acción Negativo
                    setNegativeButton("No", null)
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
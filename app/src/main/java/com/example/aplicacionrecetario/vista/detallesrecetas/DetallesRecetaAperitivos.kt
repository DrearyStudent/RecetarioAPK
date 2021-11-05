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
import com.example.aplicacionrecetario.entidades.RecetaAperitivo
import kotlinx.android.synthetic.main.activity_detalles_receta_aperitivos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaAperitivo
import java.sql.SQLException

class DetallesRecetaAperitivos : AppCompatActivity() {
    private lateinit var baseDeDatos: BaseDeDatos
    private lateinit var aperitivo: RecetaAperitivo
    private lateinit var aperitivoLiveData: LiveData<RecetaAperitivo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_receta_aperitivos)
        baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)

        // Ver en la lista y que se actualice por ID
        val idAperitivo = intent.getIntExtra("idRecetaAperitivo", 0)
        aperitivoLiveData = baseDeDatos.recetasAperitivo().obtenerPorId(idAperitivo)
        aperitivoLiveData.observe(this, Observer {
            aperitivo = it
            nombreAperitivoDetalles.text = aperitivo.nombre
            ingredientesAperitivo.text = aperitivo.ingredientes
            instruccionesAperitivo.text = aperitivo.instrucciones
            imagenAperitivoDetalles.setImageResource(aperitivo.imagen)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_receta_aperitivo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Al dar clic en Editar
            R.id.itemEditarRecetaAperitivo -> {
                val intento1 = Intent(this, VistaAgregarRecetaAperitivo::class.java)
                intento1.putExtra("aperitivo", aperitivo)
                startActivity(intento1)
            }
            //Al dar clic en Eliminar
            R.id.itemEliminarRecetaAperitivo -> {
                //Mostrar Diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea eliminar esta receta?")
                    //Acción Positivo
                    setPositiveButton("Sí") {dialogInterface: DialogInterface, i:Int ->
                        aperitivoLiveData.removeObservers(this@DetallesRecetaAperitivos)
                        try {
                            CoroutineScope(Dispatchers.IO).launch {
                                baseDeDatos.recetasAperitivo().borrarRecetaAperitivo(aperitivo)
                                this@DetallesRecetaAperitivos.finish()
                            }
                        } catch (e: SQLException) {
                            Toast.makeText(this@DetallesRecetaAperitivos, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
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
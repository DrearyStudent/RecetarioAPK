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
import com.example.aplicacionrecetario.entidades.RecetaPostre
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaPostre
import kotlinx.android.synthetic.main.activity_detalles_receta_aperitivos.*
import kotlinx.android.synthetic.main.activity_detalles_receta_ensaladas.*
import kotlinx.android.synthetic.main.activity_detalles_receta_postres.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class DetallesRecetaPostres : AppCompatActivity() {
    private lateinit var baseDeDatos: BaseDeDatos
    private lateinit var postre: RecetaPostre
    private lateinit var postreLiveData: LiveData<RecetaPostre>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_receta_postres)
        baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)

        // Ver en la lista y que se actualice por ID
        val idPostre = intent.getIntExtra("idRecetaPostre", 0)
        postreLiveData = baseDeDatos.recetasPostre().obtenerPorId(idPostre)
        postreLiveData.observe(this, Observer {
            postre = it
            nombrePostreDetalles.text = postre.nombre
            ingredientesPostre.text = postre.ingredientes
            instruccionesPostre.text = postre.instrucciones
            imagenPostreDetalles.setImageResource(postre.imagen)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_receta_postre, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Al dar clic en Editar
            R.id.itemEditarRecetaPostre -> {
                val intento1 = Intent(this, VistaAgregarRecetaPostre::class.java)
                intento1.putExtra("postre", postre)
                startActivity(intento1)
            }
            //Al dar clic en Eliminar
            R.id.itemEliminarRecetaPostre -> {
                //Mostrar Diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea eliminar esta receta?")
                    //Acción Positivo
                    setPositiveButton("Sí") { dialogInterface: DialogInterface, i:Int ->
                        postreLiveData.removeObservers(this@DetallesRecetaPostres)
                        try {
                            CoroutineScope(Dispatchers.IO).launch {
                                baseDeDatos.recetasPostre().borrarRecetaPostre(postre)
                                this@DetallesRecetaPostres.finish()
                            }
                        } catch (e: SQLException) {
                            Toast.makeText(this@DetallesRecetaPostres, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
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
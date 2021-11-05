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
import com.example.aplicacionrecetario.entidades.RecetaPlatoPrincipal
import com.example.aplicacionrecetario.vista.agregarrecetas.VistaAgregarRecetaPlatoPrincipal
import kotlinx.android.synthetic.main.activity_detalles_receta_aperitivos.*
import kotlinx.android.synthetic.main.activity_detalles_receta_ensaladas.*
import kotlinx.android.synthetic.main.activity_detalles_receta_plato_principal.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class DetallesRecetaPlatoPrincipal : AppCompatActivity() {
    private lateinit var baseDeDatos: BaseDeDatos
    private lateinit var platoPrincipal: RecetaPlatoPrincipal
    private lateinit var platoPrincipalLiveData: LiveData<RecetaPlatoPrincipal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_receta_plato_principal)
        baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)

        // Ver en la lista y que se actualice por ID
        val idPlatoPrincipal = intent.getIntExtra("idRecetaPlatoPrincipal", 0)
        platoPrincipalLiveData = baseDeDatos.recetasPlatoPrincipal().obtenerPorId(idPlatoPrincipal)
        platoPrincipalLiveData.observe(this, Observer {
            platoPrincipal = it
            nombrePlatoPrincipalDetalles.text = platoPrincipal.nombre
            ingredientesPlatoPrincipal.text = platoPrincipal.ingredientes
            instruccionesPlatoPrincipal.text = platoPrincipal.instrucciones
            imagenPlatoPrincipalDetalles.setImageResource(platoPrincipal.imagen)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_receta_plato_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Al dar clic en Editar
            R.id.itemEditarRecetaPlatoPrincipal -> {
                val intento1 = Intent(this, VistaAgregarRecetaPlatoPrincipal::class.java)
                intento1.putExtra("platoPrincipal", platoPrincipal)
                startActivity(intento1)
            }
            //Al dar clic en Eliminar
            R.id.itemEliminarRecetaPlatoPrincipal -> {
                //Mostrar Diálogo confirmación
                AlertDialog.Builder(this).apply {
                    setMessage("¿Está seguro que desea eliminar esta receta?")
                    //Acción Positivo
                    setPositiveButton("Sí") { dialogInterface: DialogInterface, i:Int ->
                        platoPrincipalLiveData.removeObservers(this@DetallesRecetaPlatoPrincipal)
                        try {
                            CoroutineScope(Dispatchers.IO).launch {
                                baseDeDatos.recetasPlatoPrincipal().borrarRecetaPlatoPrincipal(platoPrincipal)
                                this@DetallesRecetaPlatoPrincipal.finish()
                            }
                        } catch (e: SQLException) {
                            Toast.makeText(this@DetallesRecetaPlatoPrincipal, "Ocurrió un error con la base de datos, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
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
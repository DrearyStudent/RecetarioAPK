package com.example.aplicacionrecetario.accesoadatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionrecetario.entidades.RecetaAperitivo
import com.example.aplicacionrecetario.entidades.RecetaEnsalada
import com.example.aplicacionrecetario.entidades.RecetaPlatoPrincipal
import com.example.aplicacionrecetario.entidades.RecetaPostre

@Database(entities = [RecetaAperitivo::class,
    RecetaEnsalada::class,
    RecetaPlatoPrincipal::class,
    RecetaPostre::class],
    version = 4)

abstract class BaseDeDatos: RoomDatabase() {

    abstract  fun recetasAperitivo(): RecetaAperitivoDAO
    abstract  fun recetasEnsalada(): RecetaEnsaladaDAO
    abstract  fun recetasPlatoPrincipal(): RecetaPlatoPrincipalDAO
    abstract  fun recetasPostre(): RecetaPostreDAO

    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatos? = null
        fun obtenerBaseDeDatos(contexto: Context): BaseDeDatos {
            val instanciaTemporal = INSTANCE
            if (instanciaTemporal != null) {
                return instanciaTemporal
            }

            synchronized(this) {
                val instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDeDatos::class.java,
                    "app_basededatos"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instancia
                return instancia
            }
        }
    }

}
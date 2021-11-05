package com.example.aplicacionrecetario.accesoadatos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplicacionrecetario.entidades.RecetaEnsalada

@Dao
interface RecetaEnsaladaDAO {
    @Query("SELECT * FROM RecetaEnsalada")
    fun obtenerTodos(): LiveData<List<RecetaEnsalada>>

    @Query("SELECT * FROM RecetaEnsalada WHERE idRecetaEnsalada = :id")
    fun obtenerPorId(id: Int): LiveData<RecetaEnsalada>

    @Insert
    fun insertarRecetaEnsalada(vararg receta: RecetaEnsalada)

    @Update
    fun actualizarRecetaEnsalada(receta: RecetaEnsalada)

    @Delete
    fun borrarRecetaEnsalada(receta: RecetaEnsalada)
}
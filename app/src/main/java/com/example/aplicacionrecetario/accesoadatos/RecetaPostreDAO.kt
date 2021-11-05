package com.example.aplicacionrecetario.accesoadatos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplicacionrecetario.entidades.RecetaPostre

@Dao
interface RecetaPostreDAO {
    @Query("SELECT * FROM RecetaPostre")
    fun obtenerTodos(): LiveData<List<RecetaPostre>>

    @Query("SELECT * FROM RecetaPostre WHERE idRecetaPostre = :id")
    fun obtenerPorId(id: Int): LiveData<RecetaPostre>

    @Insert
    fun insertarRecetaPostre(vararg receta: RecetaPostre)

    @Update
    fun actualizarRecetaPostre(receta: RecetaPostre)

    @Delete
    fun borrarRecetaPostre(receta: RecetaPostre)
}
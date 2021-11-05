package com.example.aplicacionrecetario.accesoadatos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplicacionrecetario.entidades.RecetaAperitivo

@Dao
interface RecetaAperitivoDAO {
    @Query("SELECT * FROM RecetaAperitivo")
    fun obtenerTodos(): LiveData<List<RecetaAperitivo>>

    @Query("SELECT * FROM RecetaAperitivo WHERE idRecetaAperitivo = :id")
    fun obtenerPorId(id: Int): LiveData<RecetaAperitivo>

    @Insert
    fun insertarRecetaAperitivo(vararg receta: RecetaAperitivo)

    @Update
    fun actualizarRecetaAperitivo(receta: RecetaAperitivo)

    @Delete
    fun borrarRecetaAperitivo(receta: RecetaAperitivo)
}
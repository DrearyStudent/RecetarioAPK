package com.example.aplicacionrecetario.accesoadatos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplicacionrecetario.entidades.RecetaPlatoPrincipal

@Dao
interface RecetaPlatoPrincipalDAO {
    @Query("SELECT * FROM RecetaPlatoPrincipal")
    fun obtenerTodos(): LiveData<List<RecetaPlatoPrincipal>>

    @Query("SELECT * FROM RecetaPlatoPrincipal WHERE idRecetaPlatoPrincipal = :id")
    fun obtenerPorId(id: Int): LiveData<RecetaPlatoPrincipal>

    @Insert
    fun insertarRecetaPlatoPrincipal(vararg receta: RecetaPlatoPrincipal)

    @Update
    fun actualizarRecetaPlatoPrincipal(receta: RecetaPlatoPrincipal)

    @Delete
    fun borrarRecetaPlatoPrincipal(receta: RecetaPlatoPrincipal)
}
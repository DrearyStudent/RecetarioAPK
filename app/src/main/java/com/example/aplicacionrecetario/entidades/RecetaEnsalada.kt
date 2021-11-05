package com.example.aplicacionrecetario.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable

@Entity(tableName = "RecetaEnsalada")
class RecetaEnsalada(val nombre:String,
                     val ingredientes:String,
                     val instrucciones:String,
                     val imagen: Int,
                     @PrimaryKey(autoGenerate = true)
                     var idRecetaEnsalada: Int = 0
) : Serializable

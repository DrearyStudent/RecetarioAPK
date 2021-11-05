package com.example.aplicacionrecetario.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "RecetaAperitivo")
class RecetaAperitivo(val nombre:String,
                      val ingredientes:String,
                      val instrucciones:String,
                      val imagen: Int,
                      @PrimaryKey(autoGenerate = true)
                      var idRecetaAperitivo: Int = 0
) : Serializable
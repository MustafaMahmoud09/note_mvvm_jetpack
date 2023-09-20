package com.lomu.note_mvvm.model.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(

    tableName = "notes",
    foreignKeys = [ForeignKey(

              entity = Category::class,
              parentColumns = arrayOf("id"),
              childColumns = arrayOf("category_id"),
              onDelete = CASCADE,
              onUpdate = CASCADE

    )],


)

data class Note(

    @PrimaryKey(autoGenerate = true) val id : Int,
    val title : String,
    val body : String,
    val date : Date,
    @ColumnInfo(name = "category_id") val categoryId :Int,
    @ColumnInfo(defaultValue = "0")  val all : Int = 0

)

package com.lomu.note_mvvm.model.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(

       @PrimaryKey(autoGenerate = true) val id : Int,
       val title : String

)

package com.lomu.note_mvvm.model.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task(

       @PrimaryKey(autoGenerate = true) val id:Int,
       val title : String ,
       val date : Date ?,
       val finish:Boolean

)

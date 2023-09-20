package com.lomu.note_mvvm.model.database

import androidx.room.TypeConverter
import java.util.*

class Converters {

      @TypeConverter
      fun fromDateToLong(date:Date) : Long
                                      = date.time

      @TypeConverter
      fun fromLongToDate(date:Long) : Date
                                      = Date(date)

}
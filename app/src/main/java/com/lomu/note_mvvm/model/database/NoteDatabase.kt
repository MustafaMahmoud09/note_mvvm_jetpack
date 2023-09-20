package com.lomu.note_mvvm.model.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.data.entity.Note
import com.lomu.note_mvvm.model.data.entity.Task

@Database(

      entities = [
          Task::class,
          Category::class,
          Note::class
      ],
      version = 7,
      autoMigrations = [
          AutoMigration(from = 6, to = 7, spec = NoteDatabase.AutoMigration_3_4::class)
      ]
)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase(){

         abstract fun getNoteDao() : INoteDao

         companion object{

                 private const val databaseName = "NoteDataBase"

                 @Volatile private var noteDatabase:NoteDatabase? = null

                 fun getInstance(context: Context) : NoteDatabase?{

                              setValueNoteDatabase(context)
                              return noteDatabase

                 }//end getInstance

                 private fun setValueNoteDatabase(context: Context){

                            if(noteDatabase == null){

                                   noteDatabase = synchronized(this) {

                                       Room.databaseBuilder(
                                           context,
                                           NoteDatabase::class.java,
                                           databaseName
                                       ).build()

                                   }

                            }//end if

                 }//end setValueNoteDatabase

         }//end companion object

         @DeleteColumn(
             tableName = "categories",
             columnName = "all"
         )
         class AutoMigration_3_4 : AutoMigrationSpec

}//end NoteDatabase
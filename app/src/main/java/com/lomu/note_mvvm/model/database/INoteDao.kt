package com.lomu.note_mvvm.model.database

import androidx.room.*
import com.lomu.note_mvvm.domain.data.CategoryNumber
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.data.entity.Note
import com.lomu.note_mvvm.model.data.entity.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface INoteDao {

        @Insert
        fun insertTask(task: Task) : Completable

        @Delete
        fun deleteTask(task: Task) : Completable

        @Update
        fun updateTask(task: Task) : Completable

        @Query("SELECT * FROM tasks WHERE title LIKE :keyWord ORDER BY finish , date DESC")
        fun selectTasks(keyWord:String) : Observable<List<Task>>

        @Query("SELECT * FROM tasks")
        fun selectAllTasks() : Observable<List<Task>>

        @Insert
        fun insertCategory(category: Category) : Completable

        @Query("DELETE FROM categories WHERE id = :id")
        fun deleteCategory( id : Int) : Completable

        @Query("DELETE FROM categories")
        fun deleteAllCategory() : Completable

        @Query("SELECT * FROM categories")
        fun selectAllCategory() : Observable<List<Category>>

        @Query("SELECT categories.id , categories.title  , COUNT(notes.title) as number " +
                "FROM categories LEFT JOIN notes " +
                "ON categories.id = notes.category_id " +
                "GROUP BY  categories.id " +
                "ORDER BY categories.id DESC")
        fun selectAllCategoryAndNumberNote() : Observable<List<CategoryNumber>>

        @Insert
        fun insertNote(note: Note) : Completable

        @Delete
        fun deleteNote(note: Note) : Completable

        @Update
        fun updateNote(note: Note) : Completable

        @Query("SELECT * FROM notes WHERE (category_id = :id OR `all`=:id) AND title LIKE :title")
        fun searchByTitle(id:Int,title:String) : Observable<List<Note>>

        @Query("SELECT * FROM notes WHERE id = :id")
        fun selectNoteByIdNote(id:Int) : Observable<List<Note>>

        @Query("SELECT * FROM notes WHERE category_id = :id OR `all`= :id")
        fun selectNoteByIdCategory(id:Int) : Observable<List<Note>>

}//end INoteDao
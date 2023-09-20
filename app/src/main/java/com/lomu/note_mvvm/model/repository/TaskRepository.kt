package com.lomu.note_mvvm.model.repository

import android.content.Context
import com.lomu.note_mvvm.model.data.entity.Task
import com.lomu.note_mvvm.model.database.NoteDatabase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

object TaskRepository {

        fun insertTask(context: Context, task: Task) : Completable
                                                       = NoteDatabase
                                                            .getInstance(context)!!
                                                            .getNoteDao()
                                                            .insertTask(task)

        fun deleteTask(context: Context, task: Task) : Completable
                                                       = NoteDatabase
                                                             .getInstance(context)!!
                                                             .getNoteDao()
                                                             .deleteTask(task)

        fun updateTask(context: Context, task: Task) : Completable
                                                       = NoteDatabase
                                                             .getInstance(context)!!
                                                             .getNoteDao()
                                                             .updateTask(task)

        fun selectTasks(context: Context, key:String) : Observable<List<Task>>
                                                       = NoteDatabase
                                                              .getInstance(context)!!
                                                              .getNoteDao()
                                                              .selectTasks(key)

        fun selectAllTasks(context: Context) : Observable<List<Task>>
                                                       = NoteDatabase
                                                            .getInstance(context)!!
                                                            .getNoteDao()
                                                            .selectAllTasks()

}//end TaskRepository
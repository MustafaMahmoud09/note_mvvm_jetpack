package com.lomu.note_mvvm.model.repository

import android.content.Context
import com.lomu.note_mvvm.domain.data.CategoryNumber
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.database.NoteDatabase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

object CategoryRepository {

        fun insertCategory(context: Context, category: Category) : Completable
                                                                  = NoteDatabase
                                                                       .getInstance(context)!!
                                                                       .getNoteDao()
                                                                       .insertCategory(category)

        fun deleteCategory(context: Context, id: Int) : Completable
                                                                 = NoteDatabase
                                                                     .getInstance(context)!!
                                                                     .getNoteDao()
                                                                     .deleteCategory(id)

        fun deleteAllCategory(context: Context) : Completable
                                                        = NoteDatabase
                                                                 .getInstance(context)!!
                                                                 .getNoteDao()
                                                                 .deleteAllCategory()

        fun selectAllCategoryAndNumberNote(context: Context) : Observable<List<CategoryNumber>>
                                                                                     = NoteDatabase
                                                                                          .getInstance(context)!!
                                                                                          .getNoteDao()
                                                                                          .selectAllCategoryAndNumberNote()

}//end CategoryRepository
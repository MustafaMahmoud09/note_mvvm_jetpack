package com.lomu.note_mvvm.model.repository

import android.content.Context
import com.lomu.note_mvvm.domain.mapping.MappingFromNoteEntityToNote
import com.lomu.note_mvvm.domain.data.NoteDomain
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.data.entity.Note
import com.lomu.note_mvvm.model.database.NoteDatabase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

object NoteRepository {

       fun selectAllCategory(context: Context) : Observable<List<Category>> {

                   val data = LinkedList<Category>()

                   NoteDatabase
                           .getInstance(context)!!
                           .getNoteDao()
                           .selectAllCategory()
                           .subscribeOn(Schedulers.io())
                           .subscribe({data.addAll(it)},{})

                  data.addFirst(Category(
                      0
                        ,"all"
                  ))

                 return Observable.fromArray(data)

       }//end selectAllCategory

       fun insertNote(context: Context, note: Note) : Completable
                                                                  = NoteDatabase
                                                                          .getInstance(context)!!
                                                                          .getNoteDao()
                                                                          .insertNote(note)

       fun deleteNote(context: Context , note: Note) : Completable
                                                                  = NoteDatabase
                                                                          .getInstance(context)!!
                                                                          .getNoteDao()
                                                                          .deleteNote(note)

       fun updateNote(context: Context, note: Note) : Completable
                                                                = NoteDatabase
                                                                          .getInstance(context)!!
                                                                          .getNoteDao()
                                                                          .updateNote(note)

       fun searchByTitle(context: Context,id:Int,title:String) :Observable<List<NoteDomain>> {

           return NoteDatabase
               .getInstance(context)!!
               .getNoteDao()
               .searchByTitle(id,title).map {

                   it.map {note->

                       MappingFromNoteEntityToNote().mapper(note)
                   }

               }
       }//end searchByTitle

       fun selectNoteByIdNote(context: Context, id:Int) : Observable<List<Note>>
                                                                = NoteDatabase
                                                                        .getInstance(context)!!
                                                                        .getNoteDao()
                                                                        .selectNoteByIdNote(id)

       fun selectNoteByIdCategory(context: Context, id:Int) : Observable<List<NoteDomain>> {

           return NoteDatabase
                    .getInstance(context)!!
                    .getNoteDao()
                    .selectNoteByIdCategory(id).map {

                        it.map {note->

                             MappingFromNoteEntityToNote().mapper(note)
                        }

                   }
       }//end selectNoteByIdCategory

}//end NoteRepository
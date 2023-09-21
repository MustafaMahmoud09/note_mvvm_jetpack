package com.lomu.note_mvvm.viewModel.subViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lomu.note_mvvm.domain.data.NoteDomain
import com.lomu.note_mvvm.domain.mapping.MappingFromNoteToNoteEntity
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.data.entity.Note
import com.lomu.note_mvvm.model.repository.NoteRepository
import com.lomu.note_mvvm.view.adapter.intrerfaces.ICategoryListener
import com.lomu.note_mvvm.view.adapter.intrerfaces.INoteListener
import com.lomu.note_mvvm.viewModel.BaseViewModel
import com.lomu.note_mvvm.viewModel.constant.ONoteView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class NoteViewModel : BaseViewModel() , ICategoryListener,INoteListener{

         //delete Note
         private val _deleteNote = MutableLiveData<Note>()

         val deleteNote : LiveData<Note>
                            get() = _deleteNote

         private val _stateDelete = MutableLiveData<Boolean>()

         val stateDelete : LiveData<Boolean>
                                  = _stateDelete
        //رايح جاي
        val searchKey = MutableStateFlow("")


         //to create folder
        private val _clickFolderButton = MutableLiveData<Boolean>()

        val clickFolderButton : LiveData<Boolean>
                                 = _clickFolderButton


         //to create note
        private val _clickAddNoteButton = MutableLiveData<Boolean>()

        val clickAddNoteButton : LiveData<Boolean>
                                = _clickAddNoteButton


        //get note category
        private val _idCategoryToSelectDataForIt = MutableLiveData(0)

        val idCategoryToSelectDataForIt :LiveData<Int>
                                         get() =  _idCategoryToSelectDataForIt

       //data category
        private val _alLCategory = MutableLiveData<List<Category>>()

        val allCategory : LiveData<List<Category>>
                                    get() = _alLCategory

        //data notes
        private val _notes = MutableLiveData<List<NoteDomain>>()

        val notes : LiveData<List<NoteDomain>>
                             get() = _notes


        fun selectAllCategory(context: Context){

                ONoteView.disposeCategorySelect.clear()

                NoteRepository
                    .selectAllCategory(context)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({onNext(it,_alLCategory)},::onError)
                    .addToCompositeDisposable(ONoteView.disposeCategorySelect)

        }//end selectAllCategory

        fun deleteNote(context: Context, note: Note){

                ONoteView.disposeDelete.clear()
                NoteRepository
                    .deleteNote(context,note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::onComplete,::onError)
                    .addToCompositeDisposable(ONoteView.disposeDelete)

        }//end deleteNote

        fun updateNote(context: Context,note: Note){

                ONoteView.disposeUpdate.clear()
                NoteRepository
                    .updateNote(context,note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::onComplete,::onError)
                    .addToCompositeDisposable(ONoteView.disposeUpdate)

        }//end updateNote

         //search
         fun searchImplement(context: Context){

              customScope.launch {

                  searchKey.debounce(250).collect {

                          searchOnNoteByTitle(context,
                              idCategoryToSelectDataForIt.value!!.toInt(),it)

                  }//end collect

              }//end scope

         }//end searchImplement

         private fun searchOnNoteByTitle(context: Context, id:Int, title:String){

                ONoteView.disposeNoteSelect.clear()

                NoteRepository
                       .searchByTitle(context,id,"%$title%")
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe({onNext(it,_notes)},::onError)
                       .addToCompositeDisposable(ONoteView.disposeNoteSelect)

         }//end selectNoteByIdCategory

        fun selectNoteByIdCategory(context: Context,id:Int){

                ONoteView.disposeNoteSelect.clear()

                NoteRepository
                    .selectNoteByIdCategory(context,id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({onNext(it,_notes)},::onError)
                    .addToCompositeDisposable(ONoteView.disposeNoteSelect)

        }//end selectNoteByIdCategory

       override fun clickOnCategory(idCategory: Int) {

               _idCategoryToSelectDataForIt.postValue(idCategory)
       }

       fun clickOnFolderButton(data:Boolean){

                _clickFolderButton.postValue(data)
       }//end clickOnFolderButton

       fun clickAddNoteButton(data:Boolean){

              _clickAddNoteButton.postValue(data)
       }//end clickOnFolderButton

      override fun onLongClickListenerOnNote(note: NoteDomain) : Boolean{

               val noteEntity = MappingFromNoteToNoteEntity().mapper(note)
               _deleteNote.postValue(noteEntity)
               changeStateDelete(true)
               return true

      }//end onLongClickListenerOnNote

      fun changeStateDelete(boolean: Boolean){

             _stateDelete.postValue(boolean)
      }//end changeStateDelete
}
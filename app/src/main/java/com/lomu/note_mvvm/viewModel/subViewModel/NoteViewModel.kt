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
import com.lomu.note_mvvm.view.adapter.intrerfaces.ICategorySelectorListener
import com.lomu.note_mvvm.view.adapter.intrerfaces.INoteListener
import com.lomu.note_mvvm.viewModel.BaseViewModel
import com.lomu.note_mvvm.viewModel.constant.ONoteView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.*

@OptIn(FlowPreview::class)
class NoteViewModel : BaseViewModel() , ICategoryListener,INoteListener,ICategorySelectorListener{

         //delete Note
         private val _deleteNote = MutableLiveData<Note>()

         val deleteNote : LiveData<Note>
                            get() = _deleteNote

         private val _stateDelete = MutableLiveData<Boolean>()

         val stateDelete : LiveData<Boolean>
                                  = _stateDelete

         //add note

         private val _noteAdd = MutableLiveData<Note>()

         val noteAdd : LiveData<Note>
                          get() = _noteAdd

        private val _shareNote = MutableLiveData<String>()

        val shareNote : LiveData<String>
                               get() = _shareNote

        private val _stateShareNote = MutableLiveData<Boolean>()

       val stateShareNote : LiveData<Boolean>
                              = _stateShareNote

        private val _idCategorySelected = MutableLiveData(0)

        private val idCategorySelected : LiveData<Int>
                                      get() = _idCategorySelected

        private val _titleCategorySelected =  MutableLiveData<String>()

        val titleCategorySelected :LiveData<String>
                                    get() = _titleCategorySelected

        val title = MutableStateFlow("")

        val body = MutableStateFlow("")

        private val _date = MutableLiveData(Date())

        val date : LiveData<Date>
                     get() = _date


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

        fun addNote(context: Context, note: Note){


                NoteRepository.insertNote(context,note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::onComplete,::onError)

        }//end addNote

        fun deleteNote(context: Context, note: Note){

                NoteRepository
                    .deleteNote(context,note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::onComplete,::onError)

        }//end deleteNote

        fun updateNote(context: Context,note: Note){

                NoteRepository
                    .updateNote(context,note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::onComplete,::onError)

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

       fun clickAddNote(){

              if (title.value.trim().isNotEmpty() && body.value.trim().isNotEmpty()&&idCategorySelected.value!=0) {

                  val note = Note(
                      0,
                      title.value,
                      body.value,
                      date.value!!,
                      idCategorySelected.value!!,
                      0
                  )
                  _noteAdd.postValue(note)

              }//end data

       }//end clickAddNote

       fun clickShareNote(){

               changeStateShare(true)
               val dataSharing ="(${title.value}) (${body.value}) (${_date.value!!.toLocaleString()})"
               _shareNote.postValue(dataSharing)

       }//end clickShareNote

       fun changeStateShare(boolean: Boolean){

               _stateShareNote.postValue(boolean)
       }//end changeStateShare

      override fun onClickItemCategorySelector(item: Category) {

               _idCategorySelected.postValue(item.id)
               _titleCategorySelected.postValue(item.title)

      }//end onClickItemCategorySelector

      override fun onLongClickListenerOnNote(note: NoteDomain) : Boolean{

               changeStateDelete(true)
               _deleteNote.postValue(MappingFromNoteToNoteEntity().mapper(note))

               return true

      }//end onLongClickListenerOnNote

      fun changeStateDelete(boolean: Boolean){
             _stateDelete.postValue(boolean)
      }//end changeStateDelete
}
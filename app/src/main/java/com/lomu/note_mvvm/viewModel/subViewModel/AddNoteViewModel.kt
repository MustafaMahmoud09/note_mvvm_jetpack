package com.lomu.note_mvvm.viewModel.subViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.data.entity.Note
import com.lomu.note_mvvm.model.repository.NoteRepository
import com.lomu.note_mvvm.view.adapter.intrerfaces.ICategorySelectorListener
import com.lomu.note_mvvm.viewModel.BaseViewModel
import com.lomu.note_mvvm.viewModel.constant.OAddNoteView
import com.lomu.note_mvvm.viewModel.constant.ONoteView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class AddNoteViewModel : BaseViewModel(), ICategorySelectorListener {

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

    val titleCategorySelected : LiveData<String>
        get() = _titleCategorySelected

    val title = MutableStateFlow("")

    val body = MutableStateFlow("")

    private val _date = MutableLiveData(Date())

    val date : LiveData<Date>
        get() = _date

    //data category
    private val _alLCategory = MutableLiveData<List<Category>>()

    val allCategory : LiveData<List<Category>>
        get() = _alLCategory


    fun selectAllCategory(context: Context){

        ONoteView.disposeCategorySelect.clear()

        NoteRepository
            .selectAllCategory(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({onNext(it,_alLCategory)},::onError)
            .addToCompositeDisposable(OAddNoteView.disposeCategorySelect)

    }//end selectAllCategory


    fun addNote(context: Context, note: Note){

        OAddNoteView.disposeAdd.clear()
        NoteRepository.insertNote(context,note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onComplete,::onError)
            .addToCompositeDisposable(OAddNoteView.disposeAdd)

    }//end addNote


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

}
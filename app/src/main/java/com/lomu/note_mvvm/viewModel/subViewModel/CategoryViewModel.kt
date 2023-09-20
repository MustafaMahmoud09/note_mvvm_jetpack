package com.lomu.note_mvvm.viewModel.subViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lomu.note_mvvm.domain.data.CategoryNumber
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.repository.CategoryRepository
import com.lomu.note_mvvm.view.adapter.intrerfaces.ICategoryNumberListener
import com.lomu.note_mvvm.viewModel.BaseViewModel
import com.lomu.note_mvvm.viewModel.constant.OCategoryView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow

class CategoryViewModel : BaseViewModel() ,ICategoryNumberListener {

       //action delete item
       private val _deleteItem = MutableLiveData<Int>()

       val deleteItem : LiveData<Int>
                             = _deleteItem


       //action show dialog create
       private val _showDialogCreateFolder = MutableLiveData<Boolean>()

       val showDialogCreateFolder : LiveData<Boolean>
                              = _showDialogCreateFolder


       //رايح جاي
       val dataEdit = MutableStateFlow("")


       //action add category
       private val _clickOkCreate = MutableLiveData<String>()

       val clickOkCreate : LiveData<String>
                                 = _clickOkCreate


      //action cancel dialog add
      private val _clickCancelCreate = MutableLiveData<Boolean>()

      val clickCancelCreate : LiveData<Boolean>
                    = _clickCancelCreate


      //action show dialog delete all
      private val _clickOnButtonDeleteAll = MutableLiveData<Boolean>()

      val clickOnButtonDeleteAll :LiveData<Boolean>
                                       get() = _clickOnButtonDeleteAll


      //select all categories
      private val _categories = MutableLiveData<List<CategoryNumber>>()

      val categories :LiveData<List<CategoryNumber>>
                        get() = _categories


      fun addCategory(context: Context, category: Category){

           OCategoryView.disposeAddCategory.clear()
           CategoryRepository
                .insertCategory(context,category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onComplete,::onError)
                .addToCompositeDisposable(OCategoryView.disposeAddCategory)

      }//end addCategory


      fun deleteCategory(context: Context, id: Int){

          OCategoryView.disposeDeleteItem.clear()
          CategoryRepository
               .deleteCategory(context,id)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(::onComplete,::onError)
               .addToCompositeDisposable(OCategoryView.disposeDeleteItem)

      }//end deleteCategory


      fun deleteAllCategory(context: Context){

           OCategoryView.disposeAllDelete.clear()
           CategoryRepository
               .deleteAllCategory(context)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(::onComplete,::onError)
               .addToCompositeDisposable(OCategoryView.disposeAllDelete)

      }//end deleteAllCategory


      fun selectAllCategories(context: Context){

          OCategoryView.disposeCategorySelect.clear()
          CategoryRepository
              .selectAllCategoryAndNumberNote(context)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({onNext(it,_categories)},::onError)
              .addToCompositeDisposable(OCategoryView.disposeCategorySelect)

      }//end selectAllCategories


      fun onClickButtonDeleteAll(check: Boolean){

           _clickOnButtonDeleteAll.postValue(check)
      }//end onClickButtonDeleteAll


      fun dialogCreateOnClickOk(check:Boolean){

                val text = dataEdit.value
                if(text.trim().isNotEmpty()){

                      _clickOkCreate.postValue(text)
                }//end if

      }//end dialogCreateOnClickOk


      fun setClickOkCreate(data: String){

               _clickOkCreate.postValue(data)
      }//end setClickOkCreate


      fun dialogCreateOnClickCancel(check:Boolean){

               _clickCancelCreate.postValue(check)
      }//end dialogCreateOnClickCancel


      fun onClickCreateCategoryToShowDialog(check: Boolean){

                _showDialogCreateFolder.postValue(check)
      }//end onClickCreateCategoryToShowDialog


     override fun onLongClickOnItem(id: Int) : Boolean{

               _deleteItem.postValue(id)
               return true
     }//end onLongClickOnItem

}//end CategoryViewModel
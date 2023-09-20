package com.lomu.note_mvvm.viewModel.subViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lomu.note_mvvm.model.data.entity.Task
import com.lomu.note_mvvm.model.repository.TaskRepository
import com.lomu.note_mvvm.view.adapter.intrerfaces.ITaskListener
import com.lomu.note_mvvm.viewModel.BaseViewModel
import com.lomu.note_mvvm.viewModel.constant.OTaskView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class TaskViewModel : BaseViewModel(), ITaskListener {

        //رايح جاي
        val searchOnTask = MutableStateFlow("")

        val textAddTaskToDataBase = MutableStateFlow("")


        //finally add
        private val _textFinallyToAddedToDB = MutableLiveData<String>()

        val textFinallyToAddedToDB :LiveData<String>
                                  get() = _textFinallyToAddedToDB


        //get Tasks
        private val _tasks = MutableLiveData<List<Task>>()

        val tasks : LiveData<List<Task>>
                           get() = _tasks

        private val _allTasks = MutableLiveData<List<Task>>()

        val allTasks : LiveData<List<Task>>
                           get() = _allTasks


        // update status
        private val _updateStatus = MutableLiveData<Task>()

        val updateStatus : LiveData<Task>
                           get() = _updateStatus


        //show dialog add
        private val _showDialogAddTask = MutableLiveData<Boolean>()

        val showDialogAddTask : LiveData<Boolean>
                          get() = _showDialogAddTask


        //delete item Dialog
        private val _deleteItemDialog = MutableLiveData<Task>()

        val deleteItemDialog : LiveData<Task>
                                  get() = _deleteItemDialog


        fun addTask(context: Context,task: Task){

                    OTaskView.disposeAdd.clear()
                    TaskRepository
                        .insertTask(context,task)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(::onComplete,::onError).addToCompositeDisposable(OTaskView.disposeAdd)

        }//end addTask


        fun deleteTask(context: Context, task: Task){

                   OTaskView.disposeDelete.clear()

                   TaskRepository
                       .deleteTask(context,task)
                       .subscribeOn(Schedulers.io())
                       .subscribeOn(AndroidSchedulers.mainThread())
                       .subscribe(::onComplete,::onError).addToCompositeDisposable(OTaskView.disposeDelete)

        }//end deleteTask


        fun updateTask(context: Context,task: Task){

                   OTaskView.disposeUpdate.clear()

                   TaskRepository
                       .updateTask(context,task)
                       .subscribeOn(Schedulers.io())
                       .subscribeOn(AndroidSchedulers.mainThread())
                       .subscribe(::onComplete,::onError).addToCompositeDisposable(OTaskView.disposeUpdate)

        }//end updateTask


        fun selectTasks(context: Context){

            customScope.launch {

                searchOnTask.debounce(250).collect() {


                        OTaskView.disposeSearch.clear()

                        TaskRepository
                                .selectTasks(context,"%${it.trim()}%")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({onNext(it, _tasks) }, ::onError)
                                .addToCompositeDisposable(OTaskView.disposeSearch)

                }//end customScope

            }//end collect

        }//end selectTasksNotFinish


        fun showDialogAddTask(boolean: Boolean){

                   _showDialogAddTask.postValue(boolean)

        }//end addTask

        fun executeFinallyAddTask(text:String){

                     _textFinallyToAddedToDB.postValue(text)

        }//end executeFinallyAddTak


        override fun onClickChangeStatus(item: Task) {

                  _updateStatus.postValue(
                      Task(
                          id = item.id,
                          title = item.title,
                          date = item.date,
                          finish = !item.finish
                       )
                  )

        }//end onClickChangeStatus


        override fun onLongClickDeleteItem(item: Task) : Boolean {

                  setDataInDialogDeleteLD(item)
                  return true
        }//end onLongClickDeleteItem

        fun setDataInDialogDeleteLD(item: Task?){

              _deleteItemDialog.postValue(item)
        }//end setDataInDialogDeleteLD

        fun getDataInDialogDeleteLD() : Task? {

              return _deleteItemDialog.value
        }//end getDataInDialogDeleteLD

}//end class TaskViewModel
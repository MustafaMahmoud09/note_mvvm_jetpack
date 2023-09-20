package com.lomu.note_mvvm.view.fragment.subFragment


import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.databinding.FragmentTaskBinding
import com.lomu.note_mvvm.databinding.TaskUiDialogBinding
import com.lomu.note_mvvm.model.data.entity.Task
import com.lomu.note_mvvm.view.adapter.subAdapter.TaskFinishAdapter
import com.lomu.note_mvvm.view.fragment.BaseFragment
import com.lomu.note_mvvm.viewModel.subViewModel.TaskViewModel


class TaskFragment : BaseFragment<FragmentTaskBinding>(R.layout.fragment_task) {

          private lateinit var dialogBuilder : AlertDialog.Builder
          private lateinit var dialogCreate : AlertDialog
          private lateinit var adapter: TaskFinishAdapter
          private lateinit var taskViewModel:TaskViewModel


          override fun setupResume() {


          }//end setupResume

          override fun setup() {
               super.setup()
               adapter()
               updateStatus()
               addTask()
               postAddTask()
               deleteTask()

          }//end setup

          private fun deleteTask(){

                 taskViewModel.deleteItemDialog.observe(this,::onSuccessShowDialogDelete)

          }//end deleteTask

          private fun onSuccessShowDialogDelete(it:Task?){

                 if(it!=null){

                       showDialogDelete()
                 }

          }//end onSuccessShowDialogDelete

          private fun showDialogDelete(){

                 dialogBuilder = context?.let { AlertDialog.Builder(it) }!!
                 dialogBuilder.setMessage(resources.getString(R.string.delete_msg_dialog))

                 val dataDelete = taskViewModel.getDataInDialogDeleteLD()

                 dialogBuilder.setPositiveButton(resources.getString(R.string.delete_btn))
                                                    { _, _ -> positiveButtonDelete(dataDelete) }

                 dialogCreate = dialogBuilder.create()
                 dialogCreate.show()

                 setDataNullInDeleteItem()

          }//end showDialogDelete

          private fun positiveButtonDelete(dataDelete : Task?){

                  taskViewModel.deleteTask(activity!!.applicationContext,dataDelete!!)
          }//end positiveButtonDelete

          private fun setDataNullInDeleteItem(){

                taskViewModel.setDataInDialogDeleteLD(null)
          }//end setDataNullInDeleteItem

          //addTask
          private fun addTask() {

                  taskViewModel.showDialogAddTask.observe(this,::onSuccessShowDialogAdd)

          }//end addTask

          private fun onSuccessShowDialogAdd(it:Boolean){
                  if(it) {

                       dialogCreateTask()
                  }//end if

          }//end onSuccessShowDialogAdd

          private fun dialogCreateTask(){

                     dialogBuilder = context?.let { it1 -> AlertDialog.Builder(it1) }!!

                     val view : TaskUiDialogBinding =
                          DataBindingUtil
                                .inflate(
                                     LayoutInflater.from(fragment.root.context)
                                     , R.layout.task_ui_dialog
                                     , null
                                     , false
                                )

                    view.viewModel = taskViewModel

                    dialogBuilder.setView(view.root)

                    dialogCreate = dialogBuilder.create()
                    dialogCreate.show()

                    taskViewModel.showDialogAddTask(false)

          }//end dialog CreateTask

          private fun postAddTask(){

                     taskViewModel.textFinallyToAddedToDB.observe(this,::onSuccessPostAddTask)

          }//end postAddTask

          private fun onSuccessPostAddTask(it:String){

                   if(it.trim().isNotEmpty()){

                       taskViewModel.addTask(
                              activity!!.applicationContext
                             ,Task(
                                 0
                                   ,it
                                   ,java.util.Date()
                                   ,false
                            )
                       )

                      dialogCreate.cancel()
                      taskViewModel.textAddTaskToDataBase.value = ""
                      taskViewModel.executeFinallyAddTask("")

                   }

          }//end onSuccessPostAddTask


          override fun defineViewModel() {

                   taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
                   fragment.viewModel = taskViewModel

          }//end defineViewModel


          override fun getDataDefault() {

                  taskViewModel.apply {

                         selectTasks(activity!!.applicationContext)

                  }//end apply

          }//end getDataDefault


          private fun adapter(){

                   adapter = TaskFinishAdapter(emptyList(),taskViewModel)
                   fragment.finishRecyclerId.adapter = adapter

                   taskViewModel.tasks.observe(this,::onSuccessAdapter)

          }//end adapter

          private fun onSuccessAdapter(it:List<Task>){
                    adapter.setItem(it)

          }//end onSuccessAdapter


          private fun updateStatus(){

                      taskViewModel.updateStatus.observe(this,::onSuccessUpdateState)

          }//end updateStatus

         private fun onSuccessUpdateState(it:Task){

                        taskViewModel.searchOnTask.value = fragment.searchEdit.text.toString()
                        taskViewModel
                            .updateTask(
                                activity!!.applicationContext
                                ,it
                            )

         }//end onSuccessUpdateState

}//end TaskFragment
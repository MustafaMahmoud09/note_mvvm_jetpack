package com.lomu.note_mvvm.view.adapter.subAdapter

import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.model.data.entity.Task
import com.lomu.note_mvvm.view.adapter.BaseAdapter
import com.lomu.note_mvvm.view.adapter.intrerfaces.ITaskListener


class TaskFinishAdapter(listHer: List<Task>, listenerHer: ITaskListener)
    : BaseAdapter<Task>(listHer,listenerHer){

         override var idLayout: Int
                = R.layout.item_task_finish

//         class TaskFinishHolder(binding: View) : BaseHolder(binding){
//
//                   override var binding: ViewDataBinding
//                                = ItemTaskFinishBinding.bind(binding)
//
//         }//end TaskHolder

//         class TaskNotFinishHolder(binding: View) : BaseHolder(binding){
//
//                  override var binding: ViewDataBinding
//                                = ItemTaskNotFinishBinding.bind(binding)
//
//         }//end TaskNotFinishHolder

//        override fun setupCreateView(parent: ViewGroup, viewType: Int) = kotlin.run {
//
//                        val view = LayoutInflater
//                                         .from(parent.context)
//                                         .inflate(
//                                             idLayout
//                                             ,parent
//                                             ,false
//                                         )
//
//                        when(viewType) {
//
//                                 TYPE_TASK_FINISH -> TaskFinishHolder(view)
//                                 else -> TaskNotFinishHolder(view)
//
////                        }
////
////        }//end setupCreateView

//        override fun getItemViewType(position: Int) = kotlin.run {
//
//                     when(position){
//
//                              0 ->{
//                                  idLayout = R.layout.item_task_not_finish
//                                  TYPE_TASK_NOT_FINISH
//                              }
//                              else ->{
//                                  idLayout = R.layout.item_task_finish
//                                  TYPE_TASK_FINISH
//                              }
//
//                     }//end when
//
//        }//end getItemViewType

//        companion object{
//
//                 private const val TYPE_TASK_FINISH = 2
//                 private const val TYPE_TASK_NOT_FINISH = 3
//
//        }//end companion
}
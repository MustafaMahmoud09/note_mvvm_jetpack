package com.lomu.note_mvvm.view.adapter.intrerfaces

import com.lomu.note_mvvm.model.data.entity.Task

interface ITaskListener : IBaseListener{

         fun onClickChangeStatus(item: Task)
         fun onLongClickDeleteItem(item: Task) : Boolean

}
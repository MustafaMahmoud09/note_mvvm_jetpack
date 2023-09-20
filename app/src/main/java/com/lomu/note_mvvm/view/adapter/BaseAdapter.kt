package com.lomu.note_mvvm.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lomu.note_mvvm.BR
import com.lomu.note_mvvm.view.adapter.intrerfaces.IBaseListener

abstract class BaseAdapter<TList : Any>
     (private var list: List<TList>, private val listener:IBaseListener)
     : RecyclerView.Adapter<BaseAdapter.BaseHolder>() {

       abstract var idLayout:Int

        abstract class BaseHolder(binding: View)
              : RecyclerView.ViewHolder(binding) {
                  abstract var binding : ViewDataBinding
        }//end BaseHolder

        class FinallyHolder(binding: View)
              : BaseHolder(binding){
            //end FinallyHolder

            override var binding: ViewDataBinding
                              = DataBindingUtil.bind(binding)!!

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :BaseHolder
                                             = setupCreateView(parent,viewType)

        open fun setupCreateView(parent: ViewGroup, viewType: Int) : BaseHolder
                                                 = FinallyHolder(
                                                     LayoutInflater
                                                         .from(parent.context)
                                                         .inflate(
                                                             idLayout
                                                             ,parent
                                                             ,false
                                                         )
                                                 )


        override fun onBindViewHolder(holder: BaseHolder, position: Int) {

                           setupBind(holder,position)

        }//end onBindViewHolder

        open fun setupBind(holder: BaseHolder,position: Int){

                         val currentItem = list[position]
                         holder.binding.apply {

                                 setVariable(BR.item, currentItem)
                                 setVariable(BR.listener,listener)
                         }//end apply

        }//end setupBind

        fun getList() : List<TList> = list

        fun setItem(list: List<TList>){

                 val diffCallback = DiffUtilCallback(this.list, list)
                 this.list = list
                 val diffResult = DiffUtil.calculateDiff(diffCallback)
                 diffResult.dispatchUpdatesTo(this)

        }//end setList

        override fun getItemCount() = list.size

}
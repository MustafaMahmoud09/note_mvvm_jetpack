package com.lomu.note_mvvm.view.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>) :
    DiffUtil.Callback() {

    // old size
    override fun getOldListSize(): Int = oldList.size

    // new list size
    override fun getNewListSize(): Int = newList.size

    // if items are same
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition].hashCode()
        val newItem = newList[newItemPosition].hashCode()
        return oldItem == newItem
    }

    // check if contents are same
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition].hashCode()
        val newItem = newList[newItemPosition].hashCode()

        return oldItem == newItem
    }
}
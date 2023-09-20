package com.lomu.note_mvvm.view.adapter.subAdapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.databinding.SelectorCategoryItemBinding
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.view.adapter.intrerfaces.ICategorySelectorListener


class CategorySelectAdapter(context: Context,private var list: List<Category>
,private val iCategorySelectorListener: ICategorySelectorListener)
    : ArrayAdapter<Category>(context,R.layout.selector_category_item,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

          val selectResource
                :SelectorCategoryItemBinding
                                  = DataBindingUtil.inflate(
                                       LayoutInflater.from(parent.context),
                                       R.layout.selector_category_item,
                                       parent,
                                       false
                                  )


           selectResource.item = list[position]

           selectResource.listener = iCategorySelectorListener

           return selectResource.root

    }

    fun setList(list: List<Category>){

          this.list = list
          notifyDataSetChanged()
          Log.d("TAG",this.list.toString())
    }//end setList

}//end CategorySelectAdapter
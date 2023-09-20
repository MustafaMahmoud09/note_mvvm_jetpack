package com.lomu.note_mvvm.view.adapter.subAdapter

import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.domain.data.CategoryNumber
import com.lomu.note_mvvm.view.adapter.BaseAdapter
import com.lomu.note_mvvm.view.adapter.intrerfaces.ICategoryNumberListener

class CategoryControlAdapter(list: List<CategoryNumber>, listener:ICategoryNumberListener )
       : BaseAdapter<CategoryNumber>(list,listener) {

      override var idLayout: Int
               = R.layout.item_category_controll

}//end CategoryControlAdapter
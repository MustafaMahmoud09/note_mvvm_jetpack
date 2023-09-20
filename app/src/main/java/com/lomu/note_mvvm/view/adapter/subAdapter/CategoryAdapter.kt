package com.lomu.note_mvvm.view.adapter.subAdapter

import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.view.adapter.BaseAdapter
import com.lomu.note_mvvm.view.adapter.intrerfaces.ICategoryListener
import com.lomu.note_mvvm.viewModel.subViewModel.NoteViewModel

class CategoryAdapter(list: List<Category>,listenerHer:ICategoryListener,private val context: Fragment)
    : BaseAdapter<Category>(list,listenerHer) {

    override var idLayout: Int
                       = R.layout.item_category

    override fun setupBind(holder: BaseHolder, position: Int) {
        super.setupBind(holder, position)
        holder.binding.setVariable(BR.viewModel,ViewModelProvider(context)[NoteViewModel::class.java])
    }//end setupBind

}//end CategoryAdapter
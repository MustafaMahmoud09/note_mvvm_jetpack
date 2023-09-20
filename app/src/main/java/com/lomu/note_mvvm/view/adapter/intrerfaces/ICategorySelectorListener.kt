package com.lomu.note_mvvm.view.adapter.intrerfaces

import com.lomu.note_mvvm.model.data.entity.Category

interface ICategorySelectorListener : IBaseListener {

    fun onClickItemCategorySelector(item:Category)
}
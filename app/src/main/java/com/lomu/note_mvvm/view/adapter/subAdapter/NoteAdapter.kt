package com.lomu.note_mvvm.view.adapter.subAdapter

import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.domain.data.NoteDomain
import com.lomu.note_mvvm.view.adapter.BaseAdapter
import com.lomu.note_mvvm.view.adapter.intrerfaces.INoteListener

class NoteAdapter(list: List<NoteDomain>, listenerHer: INoteListener)
    : BaseAdapter<NoteDomain>(list,listenerHer) {

    override var idLayout: Int
                       = R.layout.item_note

}//end CategoryAdapter
package com.lomu.note_mvvm.view.adapter.intrerfaces

import com.lomu.note_mvvm.domain.data.NoteDomain

interface INoteListener : IBaseListener {

      fun onLongClickListenerOnNote(note : NoteDomain) : Boolean

}
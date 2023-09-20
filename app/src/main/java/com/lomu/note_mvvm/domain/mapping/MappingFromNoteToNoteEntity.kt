package com.lomu.note_mvvm.domain.mapping

import com.lomu.note_mvvm.domain.data.NoteDomain
import com.lomu.note_mvvm.model.data.entity.Note

class MappingFromNoteToNoteEntity
    : Mapping<NoteDomain,Note>{

    override fun mapper(data: NoteDomain): Note {

             return Note(
                 id = data.id,
                 title = data.title!!,
                 body = data.body!!,
                 categoryId = data.categoryId,
                 date = data.date
             )

    }//end mapper

}//end MappingFromNoteToNoteEntity
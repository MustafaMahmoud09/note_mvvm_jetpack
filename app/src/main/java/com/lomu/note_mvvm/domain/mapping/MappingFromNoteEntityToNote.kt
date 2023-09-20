package com.lomu.note_mvvm.domain.mapping

import com.lomu.note_mvvm.domain.data.NoteDomain
import com.lomu.note_mvvm.domain.mapping.Mapping
import com.lomu.note_mvvm.model.data.entity.Note

class MappingFromNoteEntityToNote : Mapping<Note, NoteDomain> {


       override fun mapper(data: Note): NoteDomain {

             return NoteDomain(
                 id = data.id,
                 title = data.title,
                 body = data.body,
                 date = data.date,
                 categoryId = data.categoryId,
                 all = data.all
             )

       }//end mapper

}
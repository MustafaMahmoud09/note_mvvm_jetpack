package com.lomu.note_mvvm.domain.mapping

interface Mapping<I,O>{

       fun mapper(data:I) : O

}//end Mapping
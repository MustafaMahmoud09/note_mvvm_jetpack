package com.lomu.note_mvvm.viewModel.constant

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable

object ONoteView {

      var disposeNoteSelect : CompositeDisposable = CompositeDisposable()
      var disposeCategorySelect : CompositeDisposable = CompositeDisposable()
      var disposeDelete : CompositeDisposable = CompositeDisposable()
      var disposeUpdate : CompositeDisposable = CompositeDisposable()
      var disposeAdd : CompositeDisposable = CompositeDisposable()

}//end ONoteView
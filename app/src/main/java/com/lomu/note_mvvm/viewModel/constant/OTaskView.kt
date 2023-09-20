package com.lomu.note_mvvm.viewModel.constant

import io.reactivex.rxjava3.disposables.CompositeDisposable

object OTaskView {

         var disposeSearch : CompositeDisposable = CompositeDisposable()
         var disposeAdd : CompositeDisposable = CompositeDisposable()
         var disposeDelete : CompositeDisposable = CompositeDisposable()
         var disposeUpdate : CompositeDisposable = CompositeDisposable()

}//end OTaskView
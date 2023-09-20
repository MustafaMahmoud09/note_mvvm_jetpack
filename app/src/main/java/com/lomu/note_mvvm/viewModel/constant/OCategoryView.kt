package com.lomu.note_mvvm.viewModel.constant

import io.reactivex.rxjava3.disposables.CompositeDisposable

object OCategoryView {

     var disposeCategorySelect : CompositeDisposable = CompositeDisposable()
     var disposeAddCategory : CompositeDisposable = CompositeDisposable()
     var disposeDeleteItem : CompositeDisposable = CompositeDisposable()
     var disposeAllDelete : CompositeDisposable = CompositeDisposable()

}//end OCategoryView
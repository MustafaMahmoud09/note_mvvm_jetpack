package com.lomu.note_mvvm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel(){

        protected val customScope = CoroutineScope(Dispatchers.Default)

        fun Disposable.addToCompositeDisposable(compositeDisposable: CompositeDisposable){

                   compositeDisposable.add(this)
        }//end addToCompositeDisposable

        fun onComplete(){}//onComplete

        fun onError(throwable: Throwable){}//end onError

        fun<T> onNext(data: List<T>, list: MutableLiveData<List<T>>){

                 list.postValue(data)

        }//end onNextTask

        override fun onCleared() {
              super.onCleared()
              setup()

        }//end onCleared

        open fun setup(){

             customScope.cancel()
        }//end setup
}//end BaseViewModel
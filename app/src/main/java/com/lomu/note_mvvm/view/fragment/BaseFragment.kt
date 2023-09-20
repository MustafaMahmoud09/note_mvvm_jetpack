package com.lomu.note_mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>(private val idLayout:Int) : Fragment(){//end BaseFragment

    lateinit var fragment: T

               override fun onCreateView(
                    inflater: LayoutInflater,
                    container: ViewGroup?,
                    savedInstanceState: Bundle?
               ): View? {

                   defineDataBinding(inflater, container)
                   return fragment.root

               }//end onCreateView

               private fun defineDataBinding(
                       inflater: LayoutInflater,
                       container: ViewGroup?
               ) {
                          fragment = DataBindingUtil.inflate(inflater, idLayout, container, false)
                          fragment.lifecycleOwner = this
               }//end defineDataBinding


               override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                          super.onViewCreated(view, savedInstanceState)

                          setup()
               }//end onStart

               open fun setup(){
                       defineViewModel()
                       getDataDefault()

               }//end open

               open fun defineViewModel(){}
               open fun getDataDefault(){}

               override fun onResume() {
                     super.onResume()

                     setupResume()
               }//end onResume

               abstract fun setupResume()

}//end BaseFragment
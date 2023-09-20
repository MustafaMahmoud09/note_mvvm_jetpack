package com.lomu.note_mvvm.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<T: ViewDataBinding>(private val idLayout:Int) : AppCompatActivity() {

         lateinit var activity: T

         override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setupCreate()

         }//end onCreate

         open fun setupCreate(){

                defineDataBinding()

         }//end setupCreate

        private fun defineDataBinding() {

             activity = DataBindingUtil.setContentView(
                 this,
                 idLayout
             )
             activity.lifecycleOwner = this

         }//end defineDataBinding
         override fun onResume() {
               super.onResume()
               setupResume()

         }//end onResume

         abstract fun setupResume()

}//end BaseActivity
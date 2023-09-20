package com.lomu.note_mvvm.view.util


import android.content.res.ColorStateList
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.view.adapter.BaseAdapter

@BindingAdapter(value = ["app:checkNotData"])
fun View.checkNotData(list: Int){

       if(list==0){
            this.visibility = View.VISIBLE
       }//end if

       else{
            this.visibility = View.INVISIBLE
       }//end else

}//end checkNotData

@BindingAdapter(value = ["app:checkData"])
fun View.checkData(list: Int){

        if(list != 0){
             this.visibility = View.VISIBLE
        }//end if

        else{
             this.visibility = View.INVISIBLE
        }//end else

}//end checkData

@BindingAdapter(value = ["app:checkTaskFinish"])
fun CheckBox.checkTaskFinish(check:Boolean){

        this.isChecked = check

}//end checkTaskFinish

@BindingAdapter(value = ["app:changeColor"])
fun TextView.changeColor(check: Boolean){

        if(check){

             this.setTextColor(resources.getColor(R.color.white_dark))
        }//end if

        else{

            this.setTextColor(resources.getColor(R.color.white))
        }//end else
}//end changeColor

@BindingAdapter(value = ["app:setAdapter"])
fun<T : Any> RecyclerView.setAdapter(list: List<T>){

    (this.adapter as BaseAdapter<T>).setItem(list)

}//end setAdapter

@BindingAdapter(value = ["app:itemSelected","app:thisItem"],requireAll = true)
fun ConstraintLayout.setBackGroundCategory(idSelected:Int,idItem:Int){
       try {

           if (idSelected == idItem) {

               this.setBackgroundColor(resources.getColor(R.color.theme_notes))
           }//end if
           else {

               this.setBackgroundColor(resources.getColor(R.color.theme_primary_dark))
           }//end else
       }//end try
       catch (ex:Exception){

           this.setBackgroundColor(resources.getColor(R.color.theme_primary_dark))
       }//end catch
}//end checkCategorySelectOrNo

@BindingAdapter(value = ["app:setBackgroundPress"])
fun ConstraintLayout.setBackgroundPress(check: Boolean){

         if(check){

             this.setBackgroundColor(resources.getColor(R.color.background_clicked_category))
         }//end if
         else{

             this.setBackgroundColor(resources.getColor(R.color.theme_primary_dark))
         }//end else
}//end setBackgroundPress

@BindingAdapter(value = ["app:buttonShowWhenData"])
fun Button.buttonShowWhenData(data:String){

         if(data.trim().isNotEmpty()){

                 this.setTextColor(R.color.white_1)
                 this.backgroundTintList = ColorStateList.valueOf(R.color.blue)
         }//end if
         else{

               this.setTextColor(R.color.black_1)
               this.backgroundTintList = ColorStateList.valueOf(R.color.theme_primary_dialog)
         }//end else
}//end buttonShowWhenData
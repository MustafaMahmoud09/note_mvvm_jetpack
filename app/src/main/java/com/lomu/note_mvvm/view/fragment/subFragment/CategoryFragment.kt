package com.lomu.note_mvvm.view.fragment.subFragment


import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.databinding.FragmentCategoryBinding
import com.lomu.note_mvvm.databinding.NewFolderAddedDialogBinding
import com.lomu.note_mvvm.domain.data.CategoryNumber
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.view.adapter.subAdapter.CategoryControlAdapter
import com.lomu.note_mvvm.view.fragment.BaseFragment
import com.lomu.note_mvvm.viewModel.subViewModel.CategoryViewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

         private lateinit var dialogBuilder : AlertDialog.Builder
         private lateinit var dialogCreate : AlertDialog
         lateinit var categoryViewModel : CategoryViewModel
         lateinit var categoryControlAdapter : CategoryControlAdapter

         override fun setupResume() {

         }//end setupResume

         override fun setup() {
               super.setup()
               adapter()
               observe()
               postAddCategory()
         }//end setup


        private fun tempShowDialogDelete(message:String, funDelete:() -> Unit){

                dialogBuilder = context?.let { AlertDialog.Builder(it) }!!
                dialogBuilder.setMessage(message)


                dialogBuilder.setPositiveButton(resources.getString(R.string.delete_btn))
                                        {_, _ -> funDelete() }

                dialogCreate = dialogBuilder.create()
                dialogCreate.show()


        }//end showDialogDelete


        private fun observe(){

                categoryViewModel.apply {

                    deleteItem.observe(this@CategoryFragment,::onSuccessDeleteItem)
                    clickOnButtonDeleteAll.observe(this@CategoryFragment, ::onSuccessDeleteAll)
                    showDialogCreateFolder.observe(this@CategoryFragment, ::onSuccessShowDialogCreateCategory)
                }//end apply

        }//end deleteAllItem


        private fun onSuccessDeleteItem(id:Int){

                if(id!=0){

                      tempShowDialogDelete(resources.getString(R.string.delete_msg_one_folder),::positiveButtonDeleteItem)

                }//end if
        }//end onSuccessDeleteItem

        private fun positiveButtonDeleteItem(){

            categoryViewModel.deleteItem.value?.let {
                categoryViewModel.deleteCategory(activity!!.applicationContext,
                    it
                )
            }

            categoryViewModel.onLongClickOnItem(0)
        }//end positiveButtonDeleteItem


        private fun onSuccessDeleteAll(check:Boolean){

                 if (check){

                     tempShowDialogDelete(resources.getString(R.string.delete_all_folder_msg),::positiveButtonDelete)
                     categoryViewModel.onClickButtonDeleteAll(false)
                 }//end if

        }//end onSuccessDeleteAll

         private fun positiveButtonDelete(){

                categoryViewModel.deleteAllCategory(activity!!.applicationContext)
         }//end positiveButtonDelete


         private fun onSuccessShowDialogCreateCategory(check: Boolean){

                 if(check){

                      dialogCreateCategory()
                 }//end if

                 categoryViewModel.onClickCreateCategoryToShowDialog(false)
         }//end onSuccessShowDialogCreateCategory

         private fun dialogCreateCategory(){

               dialogBuilder = context?.let { it1 -> AlertDialog.Builder(it1) }!!

               val view : NewFolderAddedDialogBinding =
                                   DataBindingUtil
                                           .inflate(
                                              LayoutInflater.from(fragment.root.context)
                                             , R.layout.new_folder_added_dialog
                                             , null
                                             , false
                                           )

               view.viewModel = categoryViewModel

               dialogBuilder.setView(view.root)

               dialogCreate = dialogBuilder.create()
               dialogCreate.show()

         }//end dialog CreateTask

         private fun postAddCategory(){

                categoryViewModel.apply {
                    clickOkCreate.observe(this@CategoryFragment, ::onSuccessPostAddCategory)
                    clickCancelCreate.observe(this@CategoryFragment,::cancel)
                }//end apply

        }//end postAddTask

        private fun cancel(check: Boolean){

            if(check) {
                dialogCreate.cancel()
                categoryViewModel.dataEdit.value = ""
            }//end if

            categoryViewModel.dialogCreateOnClickCancel(false)

        }//end cancel

        private fun onSuccessPostAddCategory(it:String){

            if(it.trim().isNotEmpty()) {

                categoryViewModel.addCategory(
                    activity!!.applicationContext, Category(
                        0, it
                    )
                )
                categoryViewModel.setClickOkCreate("")
                cancel(true)
            }

        }


        override fun defineViewModel(){

               categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
               fragment.viewModel = categoryViewModel

        }//end defineViewModel

        override fun getDataDefault(){

                categoryViewModel.selectAllCategories(activity!!.applicationContext)
        }//end getDataDefault

        private fun adapter(){

                categoryControlAdapter = CategoryControlAdapter(listOf(),categoryViewModel)
                fragment.recyclerFolder.adapter = categoryControlAdapter

                categoryViewModel.categories.observe(this,::onSuccessCategory)
        }//end adapter

        private fun onSuccessCategory(list: List<CategoryNumber>){

                categoryControlAdapter.setItem(list)
        }//end onSuccessCategory

       override fun onDestroy() {
            super.onDestroy()
            categoryViewModel.onLongClickOnItem(0)
       }
}//end CategoryFragment
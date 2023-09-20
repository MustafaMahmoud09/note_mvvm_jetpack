package com.lomu.note_mvvm.view.fragment.subFragment

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.databinding.FragmentNoteBinding
import com.lomu.note_mvvm.domain.data.NoteDomain
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.view.adapter.subAdapter.CategoryAdapter
import com.lomu.note_mvvm.view.adapter.subAdapter.NoteAdapter
import com.lomu.note_mvvm.view.fragment.BaseFragment
import com.lomu.note_mvvm.viewModel.subViewModel.NoteViewModel
import java.util.*

class NoteFragment : BaseFragment<FragmentNoteBinding>(R.layout.fragment_note) {


          private lateinit var dialogBuilder : AlertDialog.Builder
          private lateinit var dialogCreate : AlertDialog
          private lateinit var noteViewModel: NoteViewModel
          private lateinit var categoryAdapter: CategoryAdapter
          private lateinit var noteAdapter: NoteAdapter


          override fun setupResume() {

          }//end setupResume

          override fun setup() {
                super.setup()
                adapter()
                getDataNotes()
                clickListener()
                deleteNote()

          }//end setup

          private fun deleteNote(){

                  noteViewModel
                      .stateDelete
                      .observe(
                          this,
                          ::onSuccessDeleteItem
                      )

          }//end deleteNote

          private fun onSuccessDeleteItem(item: Boolean){

                  if(item){

                          showDialogDelete()
                  }//end if
          }//end onSuccessDeleteItem

         private fun showDialogDelete(){

                 noteViewModel.changeStateDelete(false)
                 dialogBuilder = context?.let { AlertDialog.Builder(it) }!!
                 dialogBuilder.setMessage(resources.getString(R.string.delete_msg_note))

                 dialogBuilder.setPositiveButton(
                     resources.getString(R.string.delete_btn)
                 ){ _, _ -> positiveButtonDelete() }

                 dialogCreate = dialogBuilder.create()
                 dialogCreate.show()

        }//end showDialogDelete

         private fun positiveButtonDelete(){

                  noteViewModel.deleteNote(
                      activity!!.applicationContext,
                      noteViewModel.deleteNote.value!!
                  )

         }//end positiveButtonDelete


          private fun clickListener(){

                 clickAddNote()
                 clickAddFolder()
          }//end clickListener

          private fun clickAddNote(){

                noteViewModel
                    .clickAddNoteButton
                    .observe(
                        this,
                        ::onSuccessAddNoteButton
                    )
          }//end clickAddFolder

          private fun onSuccessAddNoteButton(data:Boolean){

                   try {

                       if (data) {

                           val extras = FragmentNavigatorExtras(
                               fragment.floatingActionButton to "sharedAddNote"
                           )

                           val navController = Navigation.findNavController(
                                                                 context as Activity
                                                                ,R.id.nav_host_main_app
                                                               )

                           val action = MainFragmentDirections
                               .actionMainFragmentToAddNoteFragment(
                                   NoteDomain(
                                   0,"","", Date(),0,0
                               )
                               )

                           navController.navigate(action,extras)

                       }//end if
                       noteViewModel.clickAddNoteButton(false)

                   }catch (ex:Exception){}
          }//end onSuccessClickButton


          private fun clickAddFolder(){

              noteViewModel.clickFolderButton.observe(this,::onSuccessAddFolderButton)
          }//end clickAddFolder

          private fun onSuccessAddFolderButton(data:Boolean){

                try {

                    if (data) {

                         val navController = Navigation.findNavController(
                                                                   context as Activity
                                                                   ,R.id.nav_host_main_app
                                                                   )
                        navController.navigate(R.id.action_mainFragment_to_categoryFragment)

                    }//end if

                    noteViewModel.clickOnFolderButton(false)

                }catch (ex:Exception){}
          }//end onSuccessClickButton


          override fun defineViewModel() {

                 noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
                 fragment.viewModel = noteViewModel

          }//end defineViewModel

          override fun getDataDefault() {

                 noteViewModel.apply {
                       selectAllCategory(activity!!.applicationContext)
                 }

          }//end getDataDefault

          private fun getDataNotes(){

                  noteViewModel.apply {
                      idCategoryToSelectDataForIt
                          .observe(
                              this@NoteFragment,
                              ::onSuccessGetDataNotesByIdNote
                          )
                      searchImplement(
                          activity!!.applicationContext
                      )
                  }
          }//end getDataNotes

          private fun onSuccessGetDataNotesByIdNote(id:Int){

                   noteViewModel.searchImplement(activity!!.applicationContext)
          }//end onSuccessGetDataNotesByIdNote

           private fun adapter(){

                 categoryAdapter()
                 noteAdapter()

           }//end adapter

           private fun categoryAdapter(){
                    categoryAdapter = CategoryAdapter(mutableListOf(),noteViewModel,this)
                    fragment.recyclerCategory.adapter = categoryAdapter

                    noteViewModel.allCategory.observe(this,::onSuccessCategory)
           }//end categoryAdapter

           private fun onSuccessCategory(it : List<Category>){

               categoryAdapter.setItem(it)
           }//end onSuccessCategory


           private fun noteAdapter(){

                    noteAdapter = NoteAdapter(mutableListOf(),noteViewModel)
                    fragment.recyclerNote.adapter = noteAdapter

                    noteViewModel.notes.observe(this,::onSuccessNote)
           }//end noteAdapter

           private fun onSuccessNote(item: List<NoteDomain>){

                    noteAdapter.setItem(item)
           }//end onSuccessNote

}//end NoteFragment
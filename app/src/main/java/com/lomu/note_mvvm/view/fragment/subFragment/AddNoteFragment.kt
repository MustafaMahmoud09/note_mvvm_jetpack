package com.lomu.note_mvvm.view.fragment.subFragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.databinding.FragmentAddNoteBinding
import com.lomu.note_mvvm.model.data.entity.Category
import com.lomu.note_mvvm.model.data.entity.Note
import com.lomu.note_mvvm.view.adapter.subAdapter.CategorySelectAdapter
import com.lomu.note_mvvm.view.fragment.BaseFragment
import com.lomu.note_mvvm.viewModel.subViewModel.AddNoteViewModel
import java.util.*

class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note){

       lateinit var noteViewModel: AddNoteViewModel
       lateinit var categoryAdapter : CategorySelectAdapter

        override fun setup() {
            super.setup()
            addNote()
            shareNote()
            adapter()
        }//end setup

        private fun shareNote(){

            noteViewModel.shareNote.observe(this,::onSuccessShareNote)
        }//end shareNote

        private fun onSuccessShareNote(data:String){

           if(noteViewModel.stateShareNote.value == true) {
               val sendIntent: Intent = Intent().apply {
                  action = Intent.ACTION_SEND
                  putExtra(Intent.EXTRA_TEXT, data)
                  type = "text/plain"
               }

              val shareIntent = Intent.createChooser(sendIntent, null)
              startActivity(shareIntent)
           }//end if

            noteViewModel.changeStateShare(false)
        }//end onSuccessShareNote


        private fun addNote(){

            noteViewModel.noteAdd.observe(this,::onSuccessAddNote)
        }//end addNote

        private fun onSuccessAddNote(note: Note){

             noteViewModel.addNote(activity!!.applicationContext,note)
             val navController = Navigation.findNavController(
                 context as Activity, R.id.nav_host_main_app
             )
             navController.popBackStack()

        }//end onSuccessAddNote

        override fun getDataDefault() {
            super.getDataDefault()

             noteViewModel.selectAllCategory(activity!!.applicationContext)
        }//end getDataDefault

        private fun adapter(){

               noteViewModel.allCategory.observe(this,::onSuccessGetCategory)
        }//end adapter

        private fun onSuccessGetCategory(list: List<Category>){

            (list as LinkedList<Category>).removeAt(0)
            categoryAdapter = CategorySelectAdapter(activity!!.applicationContext, list,noteViewModel)
            fragment.selectorCategory.setAdapter(categoryAdapter)

        }//end onSuccessGetCategory

        override fun defineViewModel() {
              super.defineViewModel()

              noteViewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]
              fragment.viewModel = noteViewModel

        }//end defineViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
               super.onCreate(savedInstanceState)

                  knowFragmentAboutTransition()

        }//end onCreate


        private fun knowFragmentAboutTransition() {

                 sharedElementEnterTransition = TransitionInflater
                                                         .from(context)
                                                         .inflateTransition(
                                                             android.R.transition.move
                                                         )

        }//end knowFragmentAboutTransition


       override fun setupResume() {


       }//end setupResume

}
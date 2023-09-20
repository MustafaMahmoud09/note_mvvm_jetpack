package com.lomu.note_mvvm.view.fragment.subFragment

import android.app.Activity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lomu.note_mvvm.R
import com.lomu.note_mvvm.databinding.FragmentMainBinding
import com.lomu.note_mvvm.view.fragment.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main){

      override fun setupResume() {

        connectBetweenNavControllerAndBottomNavigation()

      }//end setupResume

      private fun connectBetweenNavControllerAndBottomNavigation(){

            val navController = Navigation.findNavController(
                                                   context as Activity
                                                   ,R.id.nav_host_fragment
                                                 )
            fragment.navigationItem.setupWithNavController(navController)

      }//end connectBetweenNavControllerAndBottomNavigation

}//end //end MainFragment
package com.traeen.fant.ui.current_user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.traeen.fant.ApplicationViewModel
import com.traeen.fant.ApplicationViewModelFactory
import com.traeen.fant.R
import com.traeen.fant.ui.item_display.ItemViewModel
import com.traeen.fant.ui.item_display.ItemViewModelFactory
import kotlinx.android.synthetic.main.fragment_current_user_fragment.view.*

class CurrentUserFragment : Fragment() {

    private val appViewModel: ApplicationViewModel by activityViewModels<ApplicationViewModel>{
        ApplicationViewModelFactory(activity?.application)
    }

    private val currentUserViewModel: CurrentUserViewModel by activityViewModels<CurrentUserViewModel> {
        CurrentUserViewModelFactory(activity?.application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_current_user_fragment, container, false)

        root.full_name.text = appViewModel.currentUser?.getFullName();
        root.email.text = appViewModel.currentUser?.email;

        return root;
    }

}
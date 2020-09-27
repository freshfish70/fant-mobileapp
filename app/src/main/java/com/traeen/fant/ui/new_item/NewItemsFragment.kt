package com.traeen.fant.ui.new_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.traeen.fant.R

class NewItemsFragment : Fragment() {

    private lateinit var newItemsViewModel: NewItemsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        newItemsViewModel =
                ViewModelProviders.of(this).get(NewItemsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_item, container, false)
//        val textView: TextView = root.findViewById(R.id.text_slideshow)
//        newItemsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        container?.rootView?.findViewById<FloatingActionButton>(R.id.fab)?.visibility = View.INVISIBLE

        return root
    }
}
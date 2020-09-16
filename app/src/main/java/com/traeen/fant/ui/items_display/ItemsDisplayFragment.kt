package com.traeen.fant.ui.items_display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.traeen.fant.R

class ItemsDisplayFragment : Fragment() {

    private lateinit var itemsDisplayViewModels: ItemsDisplayViewModels

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        itemsDisplayViewModels =
                ViewModelProviders.of(this).get(ItemsDisplayViewModels::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        itemsDisplayViewModels.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
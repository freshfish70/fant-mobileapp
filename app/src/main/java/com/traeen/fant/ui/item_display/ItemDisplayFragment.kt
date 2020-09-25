package com.traeen.fant.ui.item_display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.traeen.fant.Main
import com.traeen.fant.R


class ItemDisplayFragment : Fragment() {

    private val model: ItemDisplayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_view_item, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)

        model.text.observe(viewLifecycleOwner, Observer {
            (activity as Main).supportActionBar?.title = it.name
            textView.text = it.name

        })
        return root
    }
}
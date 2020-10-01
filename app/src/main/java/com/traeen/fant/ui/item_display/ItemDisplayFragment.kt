package com.traeen.fant.ui.item_display

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.traeen.fant.Main
import com.traeen.fant.R
import kotlinx.android.synthetic.main.fragment_view_item.view.*


class ItemDisplayFragment : Fragment() {

    private val model: ItemDisplayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_view_item, container, false)
        val item_name: TextView = root.item_name
        val item_price: TextView = root.item_price
        val item_description: TextView = root.item_description
//        val item_image: TextView = item_price
        container?.rootView?.findViewById<FloatingActionButton>(R.id.fab)?.visibility = View.INVISIBLE

        model.text.observe(viewLifecycleOwner, Observer {
            val name = if (it.sold) "[SOLD]" + it.name else it.name
            (activity as Main).supportActionBar?.title = name
            item_name.text = name
            item_price.text = it.price.toString()
            item_description.text = it.description
        })
        return root
    }
}
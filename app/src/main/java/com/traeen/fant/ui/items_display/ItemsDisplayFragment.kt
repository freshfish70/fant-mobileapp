package com.traeen.fant.ui.items_display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.traeen.fant.R
import com.traeen.fant.ui.items_display.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_home.view.*

class ItemsDisplayFragment : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        viewManager = LinearLayoutManager(context)

        val data = DummyContent.ITEMS

        root.items_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = ItemsListAdapter(data)
        }

        return root
    }
}
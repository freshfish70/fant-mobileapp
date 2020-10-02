package com.traeen.fant.ui.items_display

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.R
import com.traeen.fant.network.HTTPAccess
import com.traeen.fant.network.VolleyHTTP
import com.traeen.fant.shared.Item
import com.traeen.fant.ui.item_display.ItemDisplayViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*


class ItemsDisplayFragment : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager

    private val model: ItemDisplayViewModel by activityViewModels()

    var dataset: List<Item> = emptyList()

    private var http: VolleyHTTP? = null

    inner class clickListner : ItemsListAdapter.RecylerViewClickListener {
        override fun onClick(v: View?, position: Int) {
            model.setItem(dataset[position])
            val navController = findNavController()
            navController.navigate(R.id.nav_view_item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        viewManager = LinearLayoutManager(context)
        if (activity is HTTPAccess) {
            http = (activity as HTTPAccess).getHTTPInstace()
        }
        container?.rootView?.findViewById<FloatingActionButton>(R.id.fab)?.visibility = View.VISIBLE

        val listener = clickListner()

        val url = Endpoints.GET_ITEMS(1)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val jsonObject: JsonObject = JsonParser.parseString(response).asJsonObject
                val data = jsonObject.get("data")
                if (data != null) {
                    dataset = gson.fromJson(data, Array<Item>::class.java).toList()
                    root.items_list.adapter = ItemsListAdapter(dataset, listener)
                }
            },
            {
                println(it.message)
            })

        http?.addToRequestQueue(stringRequest)

        root.items_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            root.items_list.adapter = ItemsListAdapter(dataset, listener)
        }

        return root
    }

}
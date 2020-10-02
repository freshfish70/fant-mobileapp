package com.traeen.fant.ui.items_display

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
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
import com.traeen.fant.shared.ListedItem
import com.traeen.fant.ui.item_display.ItemViewModel
import com.traeen.fant.ui.item_display.ItemViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.view.*


class ItemsDisplayFragment : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var itemsListAdapter: ItemsListAdapter

    private val itemViewModel: ItemViewModel by activityViewModels<ItemViewModel> {
        ItemViewModelFactory(activity?.application)
    }

    var dataset: ArrayList<ListedItem> = ArrayList()

    private var http: VolleyHTTP? = null

    inner class clickListner : ItemsListAdapter.RecylerViewClickListener {
        override fun onClick(v: View?, position: Int) {
            itemViewModel.setItem(dataset[position])
            val navController = findNavController()
            navController.navigate(R.id.nav_view_item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        viewManager = LinearLayoutManager(context)

        if (activity is HTTPAccess) {
            http = (activity as HTTPAccess).getHTTPInstace()
        }

        val listener = clickListner()
        itemsListAdapter = ItemsListAdapter(dataset, listener);
        val url = Endpoints.GET_ITEMS(1)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val jsonObject: JsonObject = JsonParser.parseString(response).asJsonObject
                val data = jsonObject.get("data")
                if (data != null) {
                    dataset.clear()
                    gson.fromJson(data, Array<ListedItem>::class.java).forEach {
                        dataset.add(it)
                    }
                    itemsListAdapter.notifyDataSetChanged()
                }
            },
            {
                println(it.message)
            })

        http?.addToRequestQueue(stringRequest)

        root.items_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            root.items_list.adapter = itemsListAdapter
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchView = menu.findItem(R.id.items_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                itemsListAdapter.filter.filter(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)

    }

}
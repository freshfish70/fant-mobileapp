package com.traeen.fant.ui.items_display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.traeen.fant.R
import com.traeen.fant.network.Files
import com.traeen.fant.network.Item
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

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.1.100:9080/api/shop/getitems?pag=1"
        var dataset : List<Item> = emptyList()
// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var gson = Gson()
                val jsonObject: JsonObject = JsonParser.parseString(response).asJsonObject
                var data = jsonObject.get("data")
                if (data != null){
                    dataset = gson.fromJson(data, Array<Item>::class.java).toList()
                    root.items_list.adapter = ItemsListAdapter(dataset)
                }
            },
            Response.ErrorListener {
                println(it.message)
            })


// Add the request to the RequestQueue.
        queue.add(stringRequest)

        root.items_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager

        }

        return root
    }
}
package com.traeen.fant.ui.items_display

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.R
import com.traeen.fant.shared.Item

import com.traeen.fant.ui.items_display.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ItemsListAdapter(
    private val values: List<Item>
) : RecyclerView.Adapter<ItemsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.item_name.text = item.id.toString()
        holder.item_description.text = item.name
        println(item.seller?.email)
        Picasso.get().load(Endpoints.GET_IMAGE(item.id, 100)).into(holder.item_image)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item_name: TextView = view.findViewById(R.id.item_number)
        val item_description: TextView = view.findViewById(R.id.content)
        val item_image: ImageView = view.findViewById(R.id.item_image)

        override fun toString(): String {
            return super.toString() + " '" + item_description.text + "'"
        }
    }
}
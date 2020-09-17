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
import kotlinx.android.synthetic.main.list_item_card.view.*


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
        holder.itemName.text = item.name
        holder.itemDescription.text = item.description
        Picasso.get().load(Endpoints.GET_IMAGE(item.id, 100)).into(holder.itemImage)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.item_number
        val itemDescription: TextView = view.content
        val itemImage: ImageView = view.item_image

        override fun toString(): String {
            return super.toString() + " '" + itemDescription.text + "'"
        }
    }
}
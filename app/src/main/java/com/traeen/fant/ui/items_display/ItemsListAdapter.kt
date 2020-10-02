package com.traeen.fant.ui.items_display

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.traeen.fant.R
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.shared.Item
import kotlinx.android.synthetic.main.list_item_card.view.*


class ItemsListAdapter(
    private val values: List<Item>,
private val listner: RecylerViewClickListener
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

        var url = ""
        if (item.image.isNotEmpty()) url = Endpoints.GET_IMAGE(item.image.first(), 100)
        Picasso.get().load(url).error(R.drawable.ic_menu_gallery).into(holder.itemImage)

    }

    override fun getItemCount(): Int = values.size

    interface RecylerViewClickListener {
        fun onClick(v: View?, position: Int )
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val itemName: TextView = view.item_number
        val itemDescription: TextView = view.content
        val itemImage: ImageView = view.item_image

        init {
            view.setOnClickListener(this)
        }

        override fun toString(): String {
            return super.toString() + " '" + itemDescription.text + "'"
        }

        override fun onClick(v: View?) {
            listner.onClick(v, adapterPosition)
        }
    }
}
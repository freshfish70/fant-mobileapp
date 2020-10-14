package com.traeen.fant.ui.items_display

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.traeen.fant.R
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.shared.ListedItem
import kotlinx.android.synthetic.main.list_item_card.view.*


class ItemsListAdapter(
    private val values: List<ListedItem>,
    private val listner: RecylerViewClickListener
) : RecyclerView.Adapter<ItemsListAdapter.ViewHolder>(), Filterable {

    private var listedItems: List<ListedItem> = values

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listedItems[position]
        holder.itemName.text = if (item.sold) "[SOLD] " + item.name else item.name
        holder.itemDescription.text = item.description

        var url = ""
        if (item.image.isNotEmpty()) url = Endpoints.GET_IMAGE(item.image.first(), 100)
        Picasso.get().load(url).error(R.drawable.ic_menu_gallery).into(holder.itemImage)

    }

    override fun getItemCount(): Int = listedItems.size

    interface RecylerViewClickListener {
        fun onClick(v: View?, position: Int)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint.toString()
                val filterResults = FilterResults()
                if (searchString.isBlank()) {
                    filterResults.values = values
                } else {
                    val filteredItems = ArrayList<ListedItem>()
                    values.forEach {
                        if (it.name.toLowerCase()
                                .contains(searchString.toLowerCase())
                        ) {
                            filteredItems.add(it)
                        }
                    }
                    filterResults.values = filteredItems.toList()
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listedItems = results?.values as (List<ListedItem>)
                notifyDataSetChanged()
            }

        }
    }
}
package com.traeen.fant.ui.item_display

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.traeen.fant.ApplicationViewModel
import com.traeen.fant.ApplicationViewModelFactory
import com.traeen.fant.Main
import com.traeen.fant.R
import com.traeen.fant.constants.Endpoints
import kotlinx.android.synthetic.main.fragment_view_item.view.*


class ItemDisplayFragment : Fragment() {

    private lateinit var navController: NavController

    private val appViewModel: ApplicationViewModel by activityViewModels<ApplicationViewModel>{
        ApplicationViewModelFactory(activity?.application)
    }

    private val itemViewModel: ItemViewModel by activityViewModels<ItemViewModel>{
        ItemViewModelFactory(activity?.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_view_item, container, false)
        container?.rootView?.findViewById<FloatingActionButton>(R.id.fab)?.visibility =
            View.INVISIBLE
        navController = findNavController()

        val itemName = root.item_name
        val itemPrice = root.item_price
        val itemDescription = root.item_description
        val buttonBuyItem = root.item_buy_button
        val itemImage = root.display_image

        buttonBuyItem.setOnClickListener {
            if (appViewModel.isUserLoggedIn()) {
                itemViewModel.buyItem(itemViewModel.listedItem.value!!)
            } else {
                val bundle = bundleOf("return" to true)
                navController.navigate(R.id.nav_login, bundle)
            }
        }

        itemViewModel.purchased.observe(viewLifecycleOwner, Observer {
            if (it){
                itemViewModel.listedItem.value?.sold = true
            }
        })

        itemViewModel.listedItem.observe(viewLifecycleOwner, Observer {
            val name = if (it.sold) "[SOLD] " + it.name else it.name
            (activity as Main).supportActionBar?.title = name

            if (it.seller?.id == appViewModel.currentUser?.id || it.sold) {
                buttonBuyItem.visibility = View.INVISIBLE
            } else {
                buttonBuyItem.text = getText(R.string.text_login_to_buy)
            }
            // Obtain a larger image
            Picasso.get().load(Endpoints.GET_IMAGE(it.image[0], 400))
                .error(R.drawable.ic_menu_gallery).into(itemImage)
            itemName.text = name
            itemPrice.text = it.price.toString()
            itemDescription.text = it.description
        })
        return root
    }
}
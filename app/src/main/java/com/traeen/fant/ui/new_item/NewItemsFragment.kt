package com.traeen.fant.ui.new_item

import android.content.ContentProvider
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.traeen.fant.ApplicationViewModel
import com.traeen.fant.ApplicationViewModelFactory
import com.traeen.fant.R
import kotlinx.android.synthetic.main.fragment_new_item.view.*
import java.io.File


class NewItemsFragment : Fragment() {

    private lateinit var newItemsViewModel: NewItemsViewModel

    private val IMAGE_REQUEST_CODE = 100

    private var selectedImage: Uri? = null
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newItemsViewModel =
                ViewModelProviders.of(this).get(NewItemsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_item, container, false)
        imageView = root.image_view
        val imageView = root.image_view
        imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                IMAGE_REQUEST_CODE
            )
        }

        container?.rootView?.findViewById<FloatingActionButton>(R.id.fab)?.visibility = View.INVISIBLE
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQUEST_CODE) {
            val appModel = ViewModelProvider(requireActivity(), ApplicationViewModelFactory(activity?.application)).get(
                ApplicationViewModel::class.java
            )
            val contentResolver = context?.contentResolver
            imageView.setImageURI(data?.data);
            selectedImage = data?.data;

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
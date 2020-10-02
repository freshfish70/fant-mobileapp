package com.traeen.fant.ui.new_item

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.traeen.fant.*
import kotlinx.android.synthetic.main.fragment_new_item.view.*


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

        val button_add_item = root.button_add_item
        val input_name = root.input_item_name
        val input_price = root.input_item_price
        val input_description = root.input_item_description

        val newItemModel = ViewModelProvider(requireActivity(), NewItemsViewModelFactory(activity?.application)).get(
            NewItemsViewModel::class.java
        )

        imageView = root.image_view
        imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                IMAGE_REQUEST_CODE
            )
        }
        fun isFormValid(): Boolean {
            return (input_name.error == null && input_price.error == null && input_description.error == null)
        }

        button_add_item.isEnabled = false
        input_name.addTextChangedListener(textWatcherTextChanged {
            if (it != null) {
                if (it.toString().isBlank()) input_name.error =
                    getString(R.string.text_can_not_be_blank)
                else input_name.error = null
            }
            button_add_item.isEnabled = isFormValid()
        })

        input_price.addTextChangedListener(textWatcherTextChanged {
            if (it != null) {
                when {
                    it.toString().isBlank() -> input_price.error =
                        getString(R.string.text_can_not_be_blank)
                    it.toString().toInt() < 0 -> input_price.error =
                        getString(R.string.item_must_have_positive_price)
                    else -> input_price.error = null
                }
            }
            button_add_item.isEnabled = isFormValid()
        })

        input_description.addTextChangedListener(textWatcherTextChanged {
            if (it != null) {
                if (it.toString().isBlank()) input_description.error =
                    getString(R.string.text_can_not_be_blank)
                else input_description.error = null
            }
            button_add_item.isEnabled = isFormValid()
        })


        val contentResolver = context?.contentResolver!!
        button_add_item.setOnClickListener{
            if (selectedImage == null)
                Toast.makeText(context?.applicationContext, getText(R.string.item_must_have_a_picture), Toast.LENGTH_SHORT)
                    .show()
            else{
                newItemModel.addItem(Item(input_name.text.toString(), input_description.text.toString(), input_price.text.toString().toInt(), contentResolver.openInputStream(selectedImage!!)!!))
            }
        }

        container?.rootView?.findViewById<FloatingActionButton>(R.id.fab)?.visibility =
            View.INVISIBLE
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQUEST_CODE) {
            imageView.setImageURI(data?.data);
            selectedImage = data?.data;
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
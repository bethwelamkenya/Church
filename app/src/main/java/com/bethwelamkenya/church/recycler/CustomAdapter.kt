package com.bethwelamkenya.church.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bethwelamkenya.church.R

class CustomAdapter(context: Context?, resource: Int, items: List<String>) : ArrayAdapter<String?>(context!!, resource, items) {

    // method to get the view of each item
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // inflate the layout for each item
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.spinner_item_layout, null)

        // get the item at the position
        val item = getItem(position)

        // set the text and image of the item
        val textView = view.findViewById<TextView>(R.id.text1)
        val imageView = view.findViewById<ImageView>(R.id.image1)
        textView.text = item.toString()
        imageView.setImageResource(getImageResource(item))

        // return the view
        return view
    }

    // method to get the view of each item when dropdown is shown
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // same as getView() but with different layout for dropdown items
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.spinner_dropdown_item_layout, null)

        val item = getItem(position)

        val textView = view.findViewById<TextView>(R.id.text)
        val imageView = view.findViewById<ImageView>(R.id.image)
        textView.text = item.toString()
        imageView.setImageResource(getImageResource(item))

        return view;
    }

    // method to get the image resource based on the item value
    private fun getImageResource(value: String?): Int {
        return when (value) {
            "Hello" -> R.drawable.activity_feed;
            "World" -> R.drawable.add_user_male;
            else -> R.drawable.back;
        }
    }

    // method to get the position of a value in the adapter
    override fun getPosition(value: String?): Int {
        for (i in 0 until count) {
            if (getItem(i).equals(value)) {
                return i;
            }
        }
        return -1; // not found
    }
}
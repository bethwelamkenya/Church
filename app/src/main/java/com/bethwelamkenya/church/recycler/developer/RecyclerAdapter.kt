package com.bethwelamkenya.church.recycler.developer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bethwelamkenya.church.interfaces.developer.AdminClicked
import com.bethwelamkenya.church.models.Admin

class RecyclerAdapter(
        private val context: Context,
        admins: ArrayList<Admin>,
        private val adminClicked: AdminClicked
    ) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}
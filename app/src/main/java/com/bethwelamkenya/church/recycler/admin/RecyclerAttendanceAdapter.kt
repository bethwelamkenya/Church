package com.bethwelamkenya.church.recycler.admin

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bethwelamkenya.church.interfaces.admin.AttendanceClicked
import com.bethwelamkenya.church.models.MemberAttendance

class RecyclerAttendanceAdapter(
    private val context: Context,
    attendances: ArrayList<MemberAttendance>,
    private val attendanceClicked: AttendanceClicked
    ) : RecyclerView.Adapter<RecyclerAttendanceAdapter.RecyclerViewHolder>() {

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
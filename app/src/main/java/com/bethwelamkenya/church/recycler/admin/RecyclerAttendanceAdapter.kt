package com.bethwelamkenya.church.recycler.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.interfaces.admin.AttendanceClicked
import com.bethwelamkenya.church.models.Attendance
import com.bethwelamkenya.church.models.MemberAttendance
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAttendanceAdapter(
    private val context: Context,
    attendances: ArrayList<Attendance>,
    private val attendanceClicked: AttendanceClicked
    ) : RecyclerView.Adapter<RecyclerAttendanceAdapter.RecyclerViewHolder>() {

    private val attendanceList = attendances
    private val fullList = attendances

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.attendance_design, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val attendance = attendanceList[position]
        holder.id.text = attendance.id.toString()
        holder.name.text = attendance.name
        holder.residence.text = attendance.residence
        holder.status.isChecked = attendance.status != 0
        holder.status.setOnCheckedChangeListener { buttonView, isChecked ->  attendanceClicked.checkBoxChanged(isChecked, holder.status)}
    }

    override fun getItemCount(): Int {
        return attendanceList.size
    }

    fun filterList(search: String){
        attendanceList.clear()
        for (member in fullList){
            if (member.name.lowercase(Locale.ROOT).contains(search.lowercase(Locale.ROOT))){
                attendanceList.add(member)
            }
        }
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id: TextView = itemView.findViewById(R.id.memberID)
        val name: TextView = itemView.findViewById(R.id.memberName)
        val number: TextView = itemView.findViewById(R.id.memberNumber)
        val residence: TextView = itemView.findViewById(R.id.memberResidence)
        val status: CheckBox = itemView.findViewById(R.id.memberStatus)
        val mainContainer: ConstraintLayout = itemView.findViewById(R.id.mainContainer)
        val cardView: CardView = itemView.findViewById(R.id.memberCard)
    }
}
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
import java.util.*

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
        holder.attendance = attendance
        holder.id.text = attendance.id.toString()
        holder.name.text = attendance.name
        holder.number.text = attendance.number.toString()
        holder.residence.text = attendance.residence
        holder.status.isChecked = attendance.status != 0
        if (position == attendanceList.size - 1){
            holder.mainContainer.setPadding(0, 0, 0, convertDpToPixels(50, context))
        }
        holder.status.setOnCheckedChangeListener { _, isChecked ->  attendanceClicked.checkBoxChanged(attendance, isChecked, holder.status)}
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

    fun convertDpToPixels(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }


    fun convertPixelsToDp(px: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (px / (density / 160)).toInt()
    }


    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var attendance: Attendance? = null
        val id: TextView = itemView.findViewById(R.id.memberID)
        val name: TextView = itemView.findViewById(R.id.memberName)
        val number: TextView = itemView.findViewById(R.id.memberNumber)
        val residence: TextView = itemView.findViewById(R.id.memberResidence)
        val status: CheckBox = itemView.findViewById(R.id.memberStatus)
        val mainContainer: ConstraintLayout = itemView.findViewById(R.id.mainContainer)
        val cardView: CardView = itemView.findViewById(R.id.attendanceCard)
    }
}
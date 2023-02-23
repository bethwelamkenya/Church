package com.bethwelamkenya.church.recycler.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.interfaces.admin.MemberClicked
import com.bethwelamkenya.church.models.Member
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(
            private val context: Context,
            members: ArrayList<Member>,
            private val memberClicked: MemberClicked
    ) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>(){

    private val membersList = members
    private val fullList = members
    private var years = arrayOf("Community", "One", "Two", "Three", "Four", "Five")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.members_design, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val member = membersList[position]
        holder.id.text = member.id.toString()
        holder.name.text = member.name
        holder.email.text = member.email
        holder.number.text = member.number.toString()
        holder.regNo.text = member.regNo
        holder.school.text = member.school
        holder.year.text = years[member.year]
        holder.department.text = member.department
        holder.residence.text = member.residence
        holder.cardView.setOnClickListener { memberClicked.onMemberClicked(member) }
        holder.cardView.setOnLongClickListener {
            memberClicked.onMemberLongClicked(member, holder.cardView)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    fun filterList(search: String){
        membersList.clear()
        for (member in fullList){
            if (member.name.lowercase(Locale.ROOT).contains(search.lowercase(Locale.ROOT)) ||
                member.email.lowercase(Locale.ROOT).contains(search.lowercase(Locale.ROOT))){
                membersList.add(member)
            }
        }
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id: TextView = itemView.findViewById(R.id.memberID)
        val name: TextView = itemView.findViewById(R.id.memberName)
        val email: TextView = itemView.findViewById(R.id.memberEmail)
        val number: TextView = itemView.findViewById(R.id.memberNumber)
        val regNo: TextView = itemView.findViewById(R.id.memberRegNo)
        val school: TextView = itemView.findViewById(R.id.memberSchool)
        val year: TextView = itemView.findViewById(R.id.memberYear)
        val department: TextView = itemView.findViewById(R.id.memberDepartment)
        val residence: TextView = itemView.findViewById(R.id.memberResidence)
        val mainContainer: ConstraintLayout = itemView.findViewById(R.id.memberContainer)
        val cardView: CardView = itemView.findViewById(R.id.memberCard)
    }
}
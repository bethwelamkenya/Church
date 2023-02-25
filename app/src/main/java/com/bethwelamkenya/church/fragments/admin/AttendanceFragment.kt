package com.bethwelamkenya.church.fragments.admin

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.interfaces.admin.AttendanceClicked
import com.bethwelamkenya.church.models.Attendance
import com.bethwelamkenya.church.models.ListAttendance
import com.bethwelamkenya.church.models.Member
import com.bethwelamkenya.church.models.MemberAttendance
import com.bethwelamkenya.church.recycler.admin.RecyclerAttendanceAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AttendanceFragment : Fragment() , AttendanceClicked{
    private lateinit var setDate: Button
    private lateinit var fetchAttendances: Button
    private lateinit var selectedDate: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DatabaseAdapter
    private lateinit var recyclerAdapter: RecyclerAttendanceAdapter
    private var day: Int = 1
    private var month: Int = 1
    private var year: Int = 2023
    private var date: String = ""
    private var isAttendanceEditing = false
    private var attendances = ArrayList<Attendance>()
    private var members = ArrayList<Member>()
    private var tempMembers = ArrayList<Member>()
    private var allNames: MutableList<String> = ArrayList()
    private var storedNames: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attendance_admin, container, false)
        adapter = DatabaseAdapter(view.context)
        setDate = view.findViewById(R.id.setDate)
        fetchAttendances = view.findViewById(R.id.fetchAttendances)
        selectedDate = view.findViewById(R.id.selectedDate)
        recyclerView = view.findViewById(R.id.recyclerViewAttendances)
        fetchAttendances.isEnabled = false
        setDate.setOnClickListener {
            // Create a new DatePickerDialog object with the current date as the default date
            val calendar: Calendar = Calendar.getInstance()
            if (selectedDate.text.toString().isEmpty()){
                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)
            }
            val datePickerDialog = DatePickerDialog(
                view.context,
                mDateSetListener,
                year,
                month,
                day
            )

            // Show the date picker dialog
            datePickerDialog.show()
        }
        fetchAttendances.setOnClickListener { fetchTheAttendances(view) }
        return view
    }
    private val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> // Convert the selected date into a Date object
            val calendar = Calendar.getInstance()
            calendar[year, month] = dayOfMonth
            val date = calendar.time

            // Format the Date object to get the day of the week
            val simpleDateFormat = SimpleDateFormat("EEE", Locale.getDefault())
            val dayOfWeek: String = simpleDateFormat.format(date)

            val dayOfMonthStr = java.lang.String.format(Locale.getDefault(), "%02d", dayOfMonth)
            val monthStr = java.lang.String.format(Locale.getDefault(), "%02d", month + 1)
            // Do something with the selected date and day of the week
            val selectedDate = "$dayOfWeek $dayOfMonthStr/$monthStr/$year"
            Toast.makeText(
                view.context,
                "Selected Date: $selectedDate",
                Toast.LENGTH_SHORT
            ).show()
            this.date = selectedDate
            fetchAttendances.isEnabled = true
            this.selectedDate.setText(selectedDate)
        }

    private fun fillRecyclerView(view: View, attendances: ArrayList<Attendance>){
        recyclerAdapter = RecyclerAttendanceAdapter(view.context, attendances, this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        recyclerView.adapter = recyclerAdapter
    }

    private fun fetchTheAttendances(view: View) {
        if (selectedDate.text.toString().isEmpty()) {
            Toast.makeText(view.context, "Please select A Date To Continue", Toast.LENGTH_SHORT).show()
        } else {
            println("fetching data")
            if (adapter.getDates(date).size == 0) {
                isAttendanceEditing = false
                getAttendance(view)
            } else {
                isAttendanceEditing = true
                getExistingAttendance(view)
            }
        }
    }

    private fun getAttendance(view: View) {
        attendances.clear()
        val allMembers = adapter.getMembers()
        for (member in allMembers){
            attendances.add(
                Attendance(
                null,
                    member.id,
                    member.name,
                    member.residence,
                    date,
                    0
                )
            )
        }
        fillRecyclerView(view, attendances)
        isAttendanceEditing = false
    }

    private fun getExistingAttendance(view: View) {
        members.clear()
        attendances.clear()
        tempMembers.clear()
        storedNames.clear()
        allNames.clear()
//        get the members who exist in attendances databases into a list called "listAttendances"
        attendances = adapter.getAttendances(date)
        members = adapter.getMembers()
        for (attendance in attendances){
            storedNames.add(attendance.name)
        }
        for (member in members){
            allNames.add(member.name)
        }
//        remove the names in "storedNames" from "allNames"
        allNames.removeAll(storedNames)
        // Iterate over each member in members
        for (member in members) {
            // Check if the member's name is in allNames
            if (allNames.contains(member.name)) {
                // Add the member to membersWithNames
                tempMembers.add(member)
            }
        }
        for (member in tempMembers){
            attendances.add(
                Attendance(
                    null,
                    member.id,
                    member.name,
                    member.residence,
                    date,
                    0
                )
            )
        }
        fillRecyclerView(view, attendances)
        isAttendanceEditing = true

//        while (storedAttendances.next()) {
//            val box = CheckBox()
//            box.isSelected = storedAttendances.getInt("status") != 0
//            listAttendances.add(
//                ListAttendance(
//                    storedAttendances.getLong("id"),
//                    storedAttendances.getLong("memberid"),
//                    storedAttendances.getString("membername"),
//                    storedAttendances.getString("date"),
//                    box
//                )
//            )
//            storedNames.add(storedAttendances.getString("membername"))
//            val myMember = adapter.getMember(storedAttendances.getString("membername"))
//            if (myMember != null) {
//                attendances.add(
//                    Attendance(
//                        storedAttendances.getLong("id"),
//                        myMember.id,
//                        myMember.name,
//                        myMember.number,
//                        myMember.residence,
//                        storedAttendances.getString("date"),
//                        box
//                    )
//                )
//            }
//        }
//
////            get all the members into a list called "allMembersForAttendance" and put the member names into a list called "allNames"
//        while (allMembers.next()) {
//            val box = CheckBox()
//            box.isSelected = false
//            allMembersForAttendance.add(
//                Attendance(
//                    allMembers.getLong("id"),
//                    allMembers.getString("name"),
//                    allMembers.getLong("number"),
//                    allMembers.getString("residence"),
//                    myDate,
//                    box
//                )
//            )
//            allNames.add(allMembers.getString("name"))
//        }
//
////                add these other members to the "attendances" list
//        for (name in allNames) {
//            val myMember = adapter.getMember(name)
//            if (myMember != null) {
//                val box = CheckBox()
//                box.isSelected = false
//                attendances.add(
//                    Attendance(
//                        myMember.id,
//                        myMember.name,
//                        myMember.number,
//                        myMember.residence,
//                        myDate,
//                        box
//                    )
//                )
//            }
//        }
//        attendanceTable.items = attendances
//        attendanceId.cellValueFactory = PropertyValueFactory("attendanceid")
//        attendanceMemberId.cellValueFactory = PropertyValueFactory("id")
//        attendanceMemberName.cellValueFactory = PropertyValueFactory("name")
//        attendanceMemberNumber.cellValueFactory = PropertyValueFactory("number")
//        attendanceMemberResidence.cellValueFactory = PropertyValueFactory("residence")
//        attendanceStatus.cellValueFactory = PropertyValueFactory("status")
//        if (attendanceTable.items.size != 0) {
////            attendanceTable.items.removeAt(0)
//            attendanceNumber.text = attendances.size.toString() + " Members"
//        }
//        submitAttendance.isDisable = false
    }

//    private fun saveAttendance() {
//        var status: Int
////            insert each member details into the "attendance" table in the database
//        for (i in attendanceTable.items.indices) {
//            status = if (attendanceStatus.getCellObservableValue(i).value.isSelected) {
//                1
//            } else {
//                0
//            }
//            adapter.insertAttendance(
//                attendanceMemberId.getCellObservableValue(i).value,
//                attendanceMemberName.getCellObservableValue(i).value,
//                attendanceDatePicker.value.toString(), status
//            )
//        }
//        adapter.insertDate(attendanceDatePicker.value.toString())
//        stageSwitcher.switchDialogStages("Confirmation", "Attendance Submitted")
//    }

    override fun checkBoxChanged(isChecked: Boolean, checkBox: CheckBox) {
        TODO("Not yet implemented")
    }


}
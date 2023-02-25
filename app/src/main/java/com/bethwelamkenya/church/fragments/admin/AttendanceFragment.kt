package com.bethwelamkenya.church.fragments.admin

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.interfaces.admin.AttendanceClicked
import com.bethwelamkenya.church.models.Attendance
import com.bethwelamkenya.church.models.Member
import com.bethwelamkenya.church.recycler.admin.RecyclerAttendanceAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class AttendanceFragment : Fragment() , AttendanceClicked{
    private lateinit var setDate: Button
    private lateinit var fetchAttendances: Button
    private lateinit var selectedDate: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var saveAttendances: ExtendedFloatingActionButton
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
        saveAttendances = view.findViewById(R.id.saveAttendances)
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
        saveAttendances.setOnClickListener {
            if (adapter.getDates(date).size == 0){
                saveAttendances(view)
            } else{
                updateAttendance(view)
            }
        }
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
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayout.VERTICAL)
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
                    member.number,
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
            storedNames.add(attendance.name.lowercase(Locale.getDefault()))
        }
        for (member in members){
            allNames.add(member.name.lowercase(Locale.getDefault()))
        }
//        remove the names in "storedNames" from "allNames"
        allNames.removeAll(storedNames)
        // Iterate over each member in members
        for (member in members) {
            // Check if the member's name is in allNames
            if (allNames.contains(member.name.lowercase(Locale.getDefault()))) {
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
                    member.number,
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

    private fun saveAttendances(view: View) {
        // Create a temporary ArrayList to hold the updated members
        val updatedMembers = ArrayList<Attendance>()

//        for (i in 0 until recyclerAdapter.itemCount ?: 0)
// Iterate over each item in the RecyclerView's adapter
        for (i in 0 until recyclerAdapter.itemCount) {
            // Get the ViewHolder for this item
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? RecyclerAttendanceAdapter.RecyclerViewHolder

            val attendance = viewHolder!!.attendance!!
            // If the ViewHolder is not null and the CheckBox is checked, update the member's status
            if (viewHolder.status.isChecked) {
                updatedMembers.add(attendance.copy(status = 1))
//                // Create a new Member object with the updated status
//                val updatedMember = Attendance(
//                    attendance.attendanceId,
//                    attendance.id,
//                    attendance.name,
//                    attendance.number,
//                    attendance.residence,
//                    date,
//                    1 // Set the status to 1 because the CheckBox is checked
//                )
//                // Check if the updatedMembers ArrayList already contains the Member object
//                val index = updatedMembers.indexOfFirst { it.attendanceId == attendance.attendanceId }
//                if (index != -1) {
//                    // If the Member object is already in the updatedMembers ArrayList, update its status
//                    updatedMembers[index] = attendance.copy(status = 1)
//                } else {
//                    // If the Member object is not in the updatedMembers ArrayList, create a new Member object with the updated status and add it to the ArrayList
//                    updatedMembers.add(attendance.copy(status = 1))
//                }

                // Add the updated Member to the temporary ArrayList
//                updatedMembers.add(updatedMember)
            } else{
                // If the ViewHolder is not null and the CheckBox is not checked, add the original member to the temporary ArrayList
                updatedMembers.add(attendance)
            }
//            // If the ViewHolder is not null and the CheckBox is checked, update the member's status
//            if (viewHolder != null && viewHolder.status.isChecked) {
//                val member = viewHolder.member
//
//                // Create a new Member object with the updated status
//                val updatedMember = Attendance(
//                    member.attendanceId,
//                    member.id,
//                    member.name,
//                    member.number,
//                    member.residence,
//                    member.date,
//                    1 // Set the status to 1 because the CheckBox is checked
//                )
//
//                // Add the updated Member to the temporary ArrayList
//                updatedMembers.add(updatedMember)
//            } else if (viewHolder != null) {
//                // If the ViewHolder is not null and the CheckBox is not checked, add the original member to the temporary ArrayList
//                updatedMembers.add(viewHolder.member)
//            }
        }
        for (myAttendance in updatedMembers){
            adapter.insertAttendance(myAttendance)
        }
        adapter.insertDate(date)
        Toast.makeText(view.context, "Done", Toast.LENGTH_SHORT).show()

// Do something with the updated members, such as save them to a database or display them in a dialog

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
    }

    private fun updateAttendance(view: View) {
        // Create a temporary ArrayList to hold the updated members
        val updatedMembers = ArrayList<Attendance>()

//        for (i in 0 until recyclerAdapter.itemCount ?: 0)
// Iterate over each item in the RecyclerView's adapter
        for (i in 0 until recyclerAdapter.itemCount) {
            // Get the ViewHolder for this item
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? RecyclerAttendanceAdapter.RecyclerViewHolder

            val attendance = viewHolder!!.attendance!!
            // If the ViewHolder is not null and the CheckBox is checked, update the member's status
            if (viewHolder.status.isChecked) {
                updatedMembers.add(attendance.copy(status = 1))
                // Check if the updatedMembers ArrayList already contains the Member object
//                val index = updatedMembers.indexOfFirst { it.attendanceId == member?.attendanceId }
//                if (index != -1) {
//                    // If the Member object is already in the updatedMembers ArrayList, update its status
//                    updatedMembers[index] = member.copy(status = 1)
//                } else {
//                    // If the Member object is not in the updatedMembers ArrayList, create a new Member object with the updated status and add it to the ArrayList
//                    updatedMembers.add(member.copy(status = 1))
//                }

                // Create a new Member object with the updated status
//                val updatedMember = Attendance(
//                    member?.attendanceId,
//                    member?.id,
//                    member!!.name,
//                    member?.number,
//                    member?.residence,
//                    date,
//                    1 // Set the status to 1 because the CheckBox is checked
//                )
                // Check if the updatedMembers ArrayList already contains the Member object
//                val index = updatedMembers.indexOfFirst { it.attendanceId == member.attendanceId }
//                if (index != -1) {
//                    // If the Member object is already in the updatedMembers ArrayList, update its status
//                    updatedMembers[index] = member.copy(status = 1)
//                } else {
//                    // If the Member object is not in the updatedMembers ArrayList, create a new Member object with the updated status and add it to the ArrayList
//                    updatedMembers.add(member.copy(status = 1))
//                }

                // Add the updated Member to the temporary ArrayList
//                updatedMembers.add(updatedMember)
            } else{
                // If the ViewHolder is not null and the CheckBox is not checked, add the original member to the temporary ArrayList
                updatedMembers.add(viewHolder.attendance!!)
            }
        }
        for (attendance in updatedMembers){
            if (storedNames.contains(attendance.name.lowercase(Locale.getDefault()))){
                adapter.updateAttendance(attendance)
//                Toast.makeText(view.context, "updated", Toast.LENGTH_SHORT).show()
            } else{
                adapter.insertAttendance(attendance)
//                Toast.makeText(view.context, "inserted", Toast.LENGTH_SHORT).show()
            }
        }
        Toast.makeText(view.context, "Done", Toast.LENGTH_SHORT).show()
//        //            things to do for every row in the attendances table
//        for (i in attendanceTable.items.indices) {
//            //                if the checkbox is checked, status is 1 else the status is 0
//            val status = if (attendanceStatus.getCellObservableValue(i).value.isSelected) {
//                1
//            } else {
//                0
//            }
////                if the member's name is in the "storedNames" list, update them otherwise create a new entry
//            if (storedNames.contains(attendanceMemberName.getCellObservableValue(i).value.lowercase(Locale.getDefault()))) {
//                adapter.updateAttendance(
//                    attendanceId.getCellObservableValue(i).value,
//                    attendanceMemberId.getCellObservableValue(i).value,
//                    attendanceMemberName.getCellObservableValue(i).value,
//                    attendanceDatePicker.value.toString(), status
//                )
//            } else {
//                adapter.insertAttendance(
//                    attendanceMemberId.getCellObservableValue(i).value,
//                    attendanceMemberName.getCellObservableValue(i).value,
//                    attendanceDatePicker.value.toString(), status
//                )
//            }
//        }
//        stageSwitcher.switchDialogStages("Confirmation", "Attendance Submitted")
    }

    override fun checkBoxChanged(attendance: Attendance, isChecked: Boolean, checkBox: CheckBox) {
        Toast.makeText(view?.context, "Hi " + attendance.name, Toast.LENGTH_SHORT).show()
    }


}

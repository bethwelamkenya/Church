package com.bethwelamkenya.church.fragments.admin

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.models.Attendance
import com.bethwelamkenya.church.models.Member
import com.bethwelamkenya.church.models.MemberAttendance
import com.bethwelamkenya.church.recycler.admin.RecyclerSpecificAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class SpecificFragment : Fragment() {

    private lateinit var adapter: DatabaseAdapter
    private lateinit var members: ArrayList<Member>
    private lateinit var memberNames: ArrayList<String>
    private lateinit var memberSpinner: Spinner
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var recyclerAdapter: RecyclerSpecificAdapter
    private var attendances = ArrayList<Attendance>()
    private var specificAttendances = ArrayList<MemberAttendance>()
    private var tempSpecificAttendances = ArrayList<MemberAttendance>()
    private var storedDates: MutableList<String> = ArrayList()

    private lateinit var startButton: Button
    private lateinit var endButton: Button
    private lateinit var fetchAttendances: Button
    private lateinit var startTextView: EditText
    private lateinit var endTextView: EditText
    private lateinit var allDates: CheckBox
    private lateinit var specificDates: CheckBox
    private lateinit var recyclerView: RecyclerView
    private lateinit var addMember: ExtendedFloatingActionButton
    private lateinit var startDate: String
    private lateinit var endDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_specific_admin, container, false)
        adapter = DatabaseAdapter(view.context)
        members = adapter.getMembers()
        memberNames = ArrayList()
        for (member in members){
            memberNames.add(member.name)
        }
        memberSpinner = view.findViewById(R.id.selectMember)
        startButton = view.findViewById(R.id.startDateButton)
        endButton = view.findViewById(R.id.endDateButton)
        fetchAttendances = view.findViewById(R.id.fetchAttendances)
        startTextView = view.findViewById(R.id.startDate)
        endTextView = view.findViewById(R.id.endDate)
        allDates = view.findViewById(R.id.allDates)
        specificDates = view.findViewById(R.id.specificDates)
        recyclerView = view.findViewById(R.id.recyclerViewSpecific)
        addMember = view.findViewById(R.id.addMemberButton)

        startButton.isEnabled = false
        endButton.isEnabled = false
//        fetchAttendances.isEnabled = false
        allDates.isChecked = true
        specificDates.isChecked = false
        val spinnerAdapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            memberNames
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        memberSpinner.adapter = spinnerAdapter

        allDates.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                specificDates.isChecked = false
                startButton.isEnabled = false
                endButton.isEnabled = false
            } else{
                specificDates.isChecked = true
                startButton.isEnabled = true
                endButton.isEnabled = true
            }
        }

        specificDates.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                allDates.isChecked = false
                startButton.isEnabled = true
                endButton.isEnabled = true
            } else {
                allDates.isChecked = true
                startButton.isEnabled = false
                endButton.isEnabled = false
            }
        }
        startButton.setOnClickListener {
            // Create a new DatePickerDialog object with the current date as the default date
            val calendar: Calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                view.context,
                mDateSetListenerStart,
                year,
                month,
                day
            )

            // Show the date picker dialog
            datePickerDialog.show()
        }

        endButton.setOnClickListener {
            // Create a new DatePickerDialog object with the current date as the default date
            val calendar: Calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                view.context,
                mDateSetListenerEnd,
                year,
                month,
                day
            )

            // Show the date picker dialog
            datePickerDialog.show()
        }
        fetchAttendances.setOnClickListener {
            if (allDates.isChecked){
                getAllAttendances(view, memberSpinner.selectedItem.toString())
            } else{
                getSpecificAttendances(view, memberSpinner.selectedItem.toString())
            }
        }
        addMember.setOnClickListener { findNavController().navigate(R.id.action_specificFragment_to_addFragment) }
        return view
    }

    private val mDateSetListenerStart = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> // Convert the selected date into a Date object
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
        this.startDate = selectedDate
        fetchAttendances.isEnabled = true
        this.startTextView.setText(selectedDate)
    }

    private val mDateSetListenerEnd = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> // Convert the selected date into a Date object
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
        this.endDate = selectedDate
        fetchAttendances.isEnabled = true
        this.endTextView.setText(selectedDate)
    }

    private fun fillRecyclerView(view: View, attendances: ArrayList<MemberAttendance>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayout.VERTICAL)
        recyclerAdapter = RecyclerSpecificAdapter(view.context, attendances)
        recyclerView.adapter = recyclerAdapter
    }

    private fun getAllAttendances(view: View, memberName: String){
        attendances.clear()
        specificAttendances.clear()
        attendances = adapter.getAttendancesByName(memberName)
        for (attendance in attendances){
            specificAttendances.add(
                MemberAttendance(
                    attendance.attendanceId,
                    attendance.date,
                    attendance.status
                )
            )
        }
        fillRecyclerView(view, specificAttendances)
    }

    private fun getSpecificAttendances(view: View, memberName: String){
        attendances.clear()
        specificAttendances.clear()
        tempSpecificAttendances.clear()
        storedDates.clear()

        attendances = adapter.getAttendancesByName(memberName)
        val datesBetween = getDatesBetweenAPI21(startDate, endDate)
        for (attendance in attendances){
            storedDates.add(attendance.date)
            if (datesBetween.contains(attendance.date)){
                tempSpecificAttendances.add(
                    MemberAttendance(
                        attendance.attendanceId,
                        attendance.date,
                        attendance.status
                    )
                )
            }
        }
        var i = 1
        for (date in datesBetween) {
            if (storedDates.contains(date)) {
                val memNo = storedDates.indexOf(date)
                val memberAttendance = tempSpecificAttendances[memNo]
                specificAttendances.add(MemberAttendance(1.toLong(), date, memberAttendance.status))
            } else {
                specificAttendances.add(MemberAttendance(1.toLong(), date, 0))
            }
            i++
        }

        fillRecyclerView(view, specificAttendances)
    }

    // Function to get all dates between two dates
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDatesBetweenAPI26(startDate: String, endDate: String): List<String> {
        // Parse the start and end dates
        val formatter = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy")
        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)

        // Initialize an empty list to hold the dates
        val dates = mutableListOf<String>()

        // Loop over the range of dates between the start and end dates
        var current = start
        while (!current.isAfter(end)) {
            // Add the current date to the list of dates
            dates.add(current.format(formatter))

            // Move to the next date
            current = current.plusDays(1)
        }

        // Return the list of dates
        return dates
    }

    // Function to get all dates between two dates
    private fun getDatesBetweenAPI21(startDate: String, endDate: String): MutableList<String> {
        // Initialize date format and calendar
        val dateFormat = SimpleDateFormat("EEE dd/MM/yyyy", Locale.US)
        val calendar = Calendar.getInstance()

        // Parse the start and end dates
        val startDateObj = dateFormat.parse(startDate)
        val endDateObj = dateFormat.parse(endDate)

        // Set the calendar to the start date
        calendar.time = startDateObj!!

        // Initialize an empty list to hold the dates
        val dates = mutableListOf<String>()

        // Loop over the range of dates between the start and end dates
        while (!calendar.time.after(endDateObj)) {
            // Add the current date to the list of dates
            dates.add(dateFormat.format(calendar.time))

            // Move to the next date
            calendar.add(Calendar.DATE, 1)
        }

        // Return the list of dates
        return dates
    }

}
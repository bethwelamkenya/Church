package com.bethwelamkenya.church.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.interfaces.admin.AdminViewModel
import com.bethwelamkenya.church.models.Member

class EditFragment : Fragment() {

    private lateinit var cancel: Button
    private lateinit var addMember: Button
    private lateinit var id: EditText
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var regNo: EditText
    private lateinit var number: EditText
    private lateinit var school: Spinner
    private lateinit var year: Spinner
    private lateinit var department: Spinner
    private lateinit var residence: EditText
    private lateinit var adapter: DatabaseAdapter
    private var emailPattern = Regex("[a-zA-Z0-9]+@+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$")
    private var numberPattern = Regex("[0-9]*$")
    private var regNoPattern = Regex("[a-zA-Z]+/+[0-9]+/+[0-9]+$")

    private var schools = arrayOf("Engineering", "Education", "Science", "Arts", "Business", "Law", "Medicine", "Aerospace", "Community")
    private var departments = arrayOf("Media", "Keyboardist", "Worshipper", "Usher", "Technician", "Intercessor",
        "Security", "Protocol", "Sanitation", "Violinist", "Pastor", "Bishop", "None")
    private var years = arrayOf("Community", "One", "Two", "Three", "Four", "Five")
    private lateinit var member: Member
    private val sharedViewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_admin, container, false)
        member = sharedViewModel.member!!
        adapter = DatabaseAdapter(view.context)
        cancel = view.findViewById(R.id.cancel)
        addMember = view.findViewById(R.id.addNewMember)
        addMember.isEnabled = false
        id = view.findViewById(R.id.id)
        name = view.findViewById(R.id.name)
        email = view.findViewById(R.id.email)
        regNo = view.findViewById(R.id.regNo)
        number = view.findViewById(R.id.number)
        school = view.findViewById(R.id.school)
        year = view.findViewById(R.id.year)
        department = view.findViewById(R.id.department)
        residence = view.findViewById(R.id.residence)

        id.setText(member.id.toString())
        name.setText(member.name)
        email.setText(member.email)
        regNo.setText(member.regNo)
        number.setText(member.number.toString())
        var position = 0 // default position
        for (i in 0 until school.count) {
            if (school.getItemAtPosition(i).equals(member.school)) {
                position = i
                break
            }
        }
        school.setSelection(position)
        year.setSelection(member.year)
        var position1 = 0 // default position
        for (i in 0 until department.count) {
            if (department.getItemAtPosition(i).equals(member.department)) {
                position1 = i
                break
            }
        }
        department.setSelection(position1)
        residence.setText(member.residence)

        name.addTextChangedListener { validateDetails() }
        email.addTextChangedListener { validateDetails() }
        regNo.addTextChangedListener { validateDetails() }
        number.addTextChangedListener { validateDetails() }
        residence.addTextChangedListener { validateDetails() }
        addMember.setOnClickListener { updateMember(view) }
        return view
    }

    private fun validateDetails(){
        if (name.text.toString().isEmpty()){
            addMember.isEnabled = false
        } else{
            validateEmailDetails()
        }
    }

    private fun validateEmailDetails() {
        if (email.text.toString().isEmpty()){
            validateRegNoDetails()
        } else{
            if (email.text.toString().matches(emailPattern)){
                validateRegNoDetails()
            } else{
                addMember.isEnabled = false
            }
        }
    }

    private fun validateRegNoDetails() {
        if (regNo.text.toString().isEmpty()){
            validateNumberDetails()
        } else{
            if (regNo.text.toString().matches(regNoPattern)){
                validateNumberDetails()
            } else{
                addMember.isEnabled = false
            }
        }
    }

    private fun validateNumberDetails() {
        if (number.text.toString().isEmpty()){
            addMember.isEnabled = true
        } else{
            addMember.isEnabled = number.text.toString().matches(numberPattern)
        }
    }

    private fun updateMember(view: View) {
        var memberNumber: Long = 0
        if (number.text.toString().isNotEmpty()){
            memberNumber = number.text.toString().toLong()
        }
        val member = Member(
            member.id,
            name.text.toString(),
            email.text.toString(),
            regNo.text.toString(),
            memberNumber,
            school.selectedItem.toString(),
            year.selectedItemPosition,
            department.selectedItem.toString(),
            residence.text.toString())
        if (adapter.updateMember(member) != 0) {
            Toast.makeText(view.context, "Member Updated Successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editFragment_to_membersFragment)
        } else {
            Toast.makeText(view.context, "Could Not Update Member", Toast.LENGTH_SHORT).show()
        }
    }

}
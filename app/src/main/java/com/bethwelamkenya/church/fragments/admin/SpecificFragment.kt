package com.bethwelamkenya.church.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.models.Member

class SpecificFragment : Fragment() {

    private lateinit var adapter: DatabaseAdapter
    private lateinit var members: ArrayList<Member>
    private lateinit var memberNames: ArrayList<String>
    private lateinit var memberSpinner: Spinner
    private lateinit var spinnerAdapter: SpinnerAdapter

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
        val spinnerAdapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            memberNames
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        memberSpinner.adapter = spinnerAdapter
        return view
    }

}
package com.bethwelamkenya.church.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.bethwelamkenya.church.R

class HomeFragment : Fragment() {
    private lateinit var membersButton: Button
    private lateinit var attendancesButton: Button
    private lateinit var specificButton: Button
    private lateinit var accountButton: Button
    private lateinit var add: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_admin, container, false)
        membersButton = view.findViewById(R.id.membersButton)
        attendancesButton = view.findViewById(R.id.attendancesButton)
        specificButton = view.findViewById(R.id.specificButton)
        accountButton = view.findViewById(R.id.accountButton)
        add = view.findViewById(R.id.addButton)
        membersButton.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_membersFragment) }
        return view
    }

}
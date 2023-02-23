package com.bethwelamkenya.church.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.interfaces.admin.MemberClicked
import com.bethwelamkenya.church.models.Member
import com.bethwelamkenya.church.recycler.admin.RecyclerAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MembersFragment : Fragment() , MemberClicked{

    private lateinit var adapter: DatabaseAdapter
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var add: ExtendedFloatingActionButton
    private lateinit var members: ArrayList<Member>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_members_admin, container, false)
        adapter = DatabaseAdapter(view.context)
        members = adapter.getMembers()
        recyclerAdapter = RecyclerAdapter(view.context, members, this)
        recyclerView = view.findViewById(R.id.recyclerViewMembers)
        add = view.findViewById(R.id.addMemberButton)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayout.VERTICAL)
        recyclerView.adapter = recyclerAdapter
        add.setOnClickListener { findNavController().navigate(R.id.action_membersFragment_to_addFragment) }
        return view
    }

    override fun onMemberClicked(member: Member) {
        TODO("Not yet implemented")
    }

    override fun onMemberLongClicked(member: Member, cardView: CardView) {
        TODO("Not yet implemented")
    }

}
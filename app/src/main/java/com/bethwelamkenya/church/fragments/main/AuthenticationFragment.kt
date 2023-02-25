package com.bethwelamkenya.church.fragments.main

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.interfaces.main.Authentication
import com.bethwelamkenya.church.interfaces.main.MainViewModel
import com.bethwelamkenya.church.ui.AdminActivity
import com.bethwelamkenya.church.ui.DeveloperActivity
import com.bethwelamkenya.church.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth


class AuthenticationFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var continueButton: Button
    private lateinit var logIn: Button
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var resetPassword: TextView
    private lateinit var seePassword: ImageButton
    private lateinit var accounts: Spinner
    private lateinit var adapter: DatabaseAdapter
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_authentication, container, false)
        auth = FirebaseAuth.getInstance()
        adapter = DatabaseAdapter(view.context)
        if (auth.currentUser != null){
            val myActivity = activity as MainActivity
            startActivity(Intent(view.context, AdminActivity::class.java))
            myActivity.finish()
        }
        logIn = view.findViewById(R.id.logIn)
        userName = view.findViewById(R.id.userName)
        password = view.findViewById(R.id.password)
        seePassword = view.findViewById(R.id.seePassword)
        resetPassword = view.findViewById(R.id.resetPassword)
        accounts = view.findViewById(R.id.accounts)

        logIn.isEnabled = false
        userName.addTextChangedListener { validateDetails() }
        password.addTextChangedListener { validateDetails() }
        logIn.setOnClickListener { startLogIn(view) }
        seePassword.setOnClickListener {
            isVisible != isVisible
            if (isVisible){
                password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else{
                println("Password\npassword\npassword")
                password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
        resetPassword.setOnClickListener{ Toast.makeText(view.context, "Not Yet Implemented", Toast.LENGTH_SHORT).show()}


        accounts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                // change the selected value of spinner2 based on position
                validateDetails()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }
//        continueButton.setOnClickListener {
//        }
        return view
    }


    private fun validateDetails() {
        if (accounts.selectedItemPosition == 1) {
            logIn.isEnabled = userName.text.toString().isNotEmpty()
        } else {
            logIn.isEnabled = userName.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()
        }
    }

    private fun startLogIn(view: View) {
        when(accounts.selectedItemPosition){
            0 -> {
                if (userName.text.toString() == "bethu" && password.text.toString() == "9852"){
                    val myActivity = activity as MainActivity
                    startActivity(
                        Intent(
                            view.context,
                            AdminActivity::class.java
                        )
                    )
                    myActivity.finish()
                } else {
                    Toast.makeText(view.context, "Invalid Details Passed", Toast.LENGTH_SHORT).show()
                }
            }
            1 -> {
                sharedViewModel.userNumber = userName.text.toString()
                findNavController().navigate(R.id.action_authenticationFragment_to_OTPFragment)
//                authentication.userName(userName.text.toString())
//                if (adapter.getMember(userName.text.toString()).size != 0){
//                    val myActivity = activity as MainActivity
//                    val intent = Intent(view.context, MemberActivity::class.java)
//                    intent.putExtra("member", userName.text.toString())
//                    startActivity(intent)
//                    myActivity.finish()
//                } else{
//                    Toast.makeText(view.context, "The Member Does Not Exist", Toast.LENGTH_SHORT).show()
//                }
            }
            else -> {
                if (userName.text.toString() == "bethu" && password.text.toString() == "9852"){
                    val myActivity = activity as MainActivity
                    startActivity(
                        Intent(
                            view.context,
                            DeveloperActivity::class.java
                        )
                    )
                    myActivity.finish()
                } else {
                    Toast.makeText(view.context, "Invalid Details Passed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
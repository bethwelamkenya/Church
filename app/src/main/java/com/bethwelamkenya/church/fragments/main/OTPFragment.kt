package com.bethwelamkenya.church.fragments.main

import android.app.ProgressDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.interfaces.main.MainViewModel
import com.bethwelamkenya.church.ui.AdminActivity
import com.bethwelamkenya.church.ui.MainActivity
import com.chaos.view.PinView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class OTPFragment : Fragment() {

    private lateinit var textView: TextView
    private var phoneNumber: String = ""
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var verificationID: String
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: ProgressDialog
    private lateinit var mainActivity: MainActivity
    private lateinit var pinView: PinView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_o_t_p, container, false)
        auth = FirebaseAuth.getInstance()
        mainActivity = activity as MainActivity
        dialog = ProgressDialog(view.context)
        dialog.setMessage("Verifying Number and Sending an OTP.")
        dialog.setCancelable(false)
        dialog.show()
        textView = view.findViewById(R.id.textView)
        pinView = view.findViewById(R.id.otpView)
        phoneNumber = sharedViewModel.userNumber
        textView.text = phoneNumber
        val options = PhoneAuthOptions.newBuilder().setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(mainActivity)
            .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(view.context, "Unable To Verify", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(verifyID: String, forceResendingToken: ForceResendingToken) {
                    super.onCodeSent(verifyID, forceResendingToken)
                    dialog.dismiss()
                    verificationID = verifyID
//                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                    val manager = getSystemService(view.context,
//                        Class.forName(INPUT_METHOD_SERVICE)
//                    ) as InputMethodManager
//                    manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//                    manager.showSoftInput(view, 0)
                    pinView.requestFocus()
                }

            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do something before text changed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do something when text changed
            }

            override fun afterTextChanged(s: Editable) {
                // Do something after text changed
                if (s.length == pinView.itemCount) {
                    val authText = PhoneAuthProvider.getCredential(verificationID, s.toString())
                    auth.signInWithCredential(authText).addOnCompleteListener {
                        if (it.isSuccessful){
                            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putBoolean("isLogIn", true).apply()
                            val myActivity = activity as MainActivity
                            startActivity(
                                Intent(
                                    view.context,
                                    AdminActivity::class.java
                                )
                            )
                            myActivity.finish()
                        } else{
                            Toast.makeText(view.context, "Cold NotVerify You. Please Try Again", Toast.LENGTH_SHORT).show()
                        }
                    }
                    // Check if PIN is correct
//                    if (s.toString() == authText) {
//                        // Show success message
//                    } else {
//                        // Show error message
//                    }
                }
            }
        })
//        pinView.setPinViewEventListener(object : PinViewEventListener() {
//            fun onDataEntered(pinview: PinView?, fromUser: Boolean) {
//                // Do something when PIN is entered
//                Toast.makeText(view.context, "PIN entered", Toast.LENGTH_SHORT).show()
//            }
//        })
        return view
    }
}
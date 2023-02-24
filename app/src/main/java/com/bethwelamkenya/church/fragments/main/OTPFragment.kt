package com.bethwelamkenya.church.fragments.main

import android.app.ProgressDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.interfaces.main.MainViewModel
import com.bethwelamkenya.church.ui.MainActivity
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
        phoneNumber = sharedViewModel.userName
        textView.text = phoneNumber
        val options = PhoneAuthOptions.newBuilder().setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(mainActivity)
            .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    TODO("Not yet implemented")
                }

                override fun onCodeSent(verifyID: String, forceResendingToken: ForceResendingToken) {
                    super.onCodeSent(verifyID, forceResendingToken)
                    dialog.dismiss()
                    verificationID = verifyID
//                    val manager = Context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    val manager = getSystemService(view.context,
                        Class.forName(INPUT_METHOD_SERVICE)
                    ) as InputMethodManager
                    manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    manager.showSoftInput(view, 0)
                }

            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        return view
    }
}
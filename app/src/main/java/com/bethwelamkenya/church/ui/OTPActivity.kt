package com.bethwelamkenya.church.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bethwelamkenya.church.databinding.ActivityMainBinding
import com.bethwelamkenya.church.databinding.ActivityOtpBinding

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
package com.bethwelamkenya.church.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bethwelamkenya.church.databinding.ActivityMainBinding
import com.bethwelamkenya.church.databinding.ActivityMemberBinding

class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
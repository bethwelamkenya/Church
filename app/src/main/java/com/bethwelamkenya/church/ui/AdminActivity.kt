package com.bethwelamkenya.church.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bethwelamkenya.church.databinding.ActivityAdminBinding
import com.bethwelamkenya.church.databinding.ActivityMainBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
package com.bethwelamkenya.church.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bethwelamkenya.church.databinding.ActivityDeveloperBinding
import com.bethwelamkenya.church.databinding.ActivityMainBinding

class DeveloperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeveloperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
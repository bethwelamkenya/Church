package com.bethwelamkenya.church.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bethwelamkenya.church.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
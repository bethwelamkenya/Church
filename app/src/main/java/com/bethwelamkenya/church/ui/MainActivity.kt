package com.bethwelamkenya.church.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.database.DatabaseAdapter
import com.bethwelamkenya.church.databinding.ActivityMainBinding
import com.bethwelamkenya.church.fragments.main.OTPFragment
import com.bethwelamkenya.church.interfaces.main.Authentication
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() , Authentication{

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navigationHostFragment: NavHostFragment
    private val isVisible = false
    private lateinit var adapter: DatabaseAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = DatabaseAdapter(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navigationHostFragment.navController
        setupActionBarWithNavController(navController)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        if (sharedPreferences.getBoolean("isLogIn", false)){
//            startActivity(Intent(this, AdminActivity::class.java))
//            this.finish()
//        }
    }

    private fun myTheme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when(sharedPreferences?.getString("themes", "system")){
            "system" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            "battery" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun userName(userName: String) {
        val bundle = Bundle()
        bundle.putString("user_name", userName)
        val transaction = this.supportFragmentManager.beginTransaction()
        val otpFragment = OTPFragment()
        otpFragment.arguments = bundle
//        findNavController(R.id.fragmentContainerView).navigate(otpFragment)
//        findNavController(R.id.fragmentContainerView).navigate(R.id.action_authenticationFragment_to_OTPFragment)
//        transaction.
    }
}
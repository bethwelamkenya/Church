package com.bethwelamkenya.church.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.databinding.ActivityDeveloperBinding
import com.bethwelamkenya.church.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class DeveloperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeveloperBinding
    private lateinit var navController: NavController
    private lateinit var navigationHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        member = intent.getSerializableExtra("member") as ArrayList<Member>
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navigationHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    private fun navigateToHome() {
        when (navController.currentDestination?.id){
            R.id.homeFragmentDeveloper -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment2_self)
            }
            R.id.adminsFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_adminsFragment_to_homeFragment2)
            }
            R.id.addFragmentDeveloper -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_addFragment2_to_homeFragment2)
            }
            R.id.editFragmentDeveloper -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_editFragment2_to_homeFragment2)
            }
        }
    }

    private fun navigateToAllAdmins(){
        when (navController.currentDestination?.id){
            R.id.homeFragmentDeveloper -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment2_to_adminsFragment)
            }
            R.id.adminsFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_adminsFragment_self)
            }
            R.id.addFragmentDeveloper -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_addFragment2_to_adminsFragment)
            }
            R.id.editFragmentDeveloper -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_editFragmentDeveloper_to_adminsFragment)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu_developer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                navigateToAllAdmins()
            }
            R.id.home -> {
                navigateToHome()
            }
            R.id.admins -> {
                navigateToAllAdmins()
            }
            R.id.logOut -> {
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }
            R.id.exit -> {
                exitProcess(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
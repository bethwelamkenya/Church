package com.bethwelamkenya.church.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.bethwelamkenya.church.R
import com.bethwelamkenya.church.databinding.ActivityAdminBinding
import kotlin.system.exitProcess

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var navController: NavController
    private lateinit var navigationHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navigationHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    private fun navigateToHome() {
        when (navController.currentDestination?.id){
            R.id.homeFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_self)
            }
            R.id.membersFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_membersFragment_to_homeFragment)
            }
            R.id.attendanceFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment_to_homeFragment)
            }
            R.id.specificFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_specificFragment_to_homeFragment)
            }
            R.id.accountFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment_to_homeFragment)
            }
            R.id.addFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_addFragment_to_homeFragment)
            }
            R.id.editFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_editFragment_to_homeFragment)
            }
        }
    }

    private fun navigateToAllMembers(){
        when (navController.currentDestination?.id){
            R.id.homeFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_membersFragment)
            }
            R.id.membersFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_membersFragment_self)
            }
            R.id.attendanceFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment_to_membersFragment)
            }
            R.id.specificFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_specificFragment_to_membersFragment)
            }
            R.id.accountFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment_to_membersFragment)
            }
            R.id.addFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_addFragment_to_membersFragment)
            }
            R.id.editFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_editFragment_to_membersFragment)
            }
        }
    }

    private fun navigateAllAttendances(){
        when (navController.currentDestination?.id){
            R.id.homeFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_attendanceFragment)
            }
            R.id.membersFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_membersFragment_to_attendanceFragment)
            }
            R.id.attendanceFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment_self)
            }
            R.id.specificFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_specificFragment_to_attendanceFragment)
            }
            R.id.accountFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment_to_attendanceFragment)
            }
            R.id.addFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_addFragment_to_attendanceFragment)
            }
            R.id.editFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_editFragment_to_attendanceFragment)
            }
        }
    }

    private fun navigateToSpecificAttendances(){
        when (navController.currentDestination?.id){
            R.id.homeFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_specificFragment)
            }
            R.id.membersFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_membersFragment_to_specificFragment)
            }
            R.id.attendanceFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment_to_specificFragment)
            }
            R.id.specificFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_specificFragment_self)
            }
            R.id.accountFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment_to_specificFragment)
            }
            R.id.addFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_addFragment_to_specificFragment)
            }
            R.id.editFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_editFragment_to_specificFragment)
            }
        }
    }

    private fun navigateToMyAccount(){
        when (navController.currentDestination?.id){
            R.id.homeFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_accountFragment)
            }
            R.id.membersFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_membersFragment_to_accountFragment)
            }
            R.id.attendanceFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment_to_accountFragment)
            }
            R.id.specificFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_specificFragment_to_accountFragment)
            }
            R.id.accountFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment_self)
            }
            R.id.addFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_addFragment_to_accountFragment)
            }
            R.id.editFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_editFragment_to_accountFragment)
            }
        }
    }

    private fun navigateToAddMember(){
        when (navController.currentDestination?.id){
            R.id.homeFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_addFragment)
            }
            R.id.membersFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_membersFragment_to_addFragment)
            }
            R.id.attendanceFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment_to_addFragment)
            }
            R.id.specificFragment -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_specificFragment_to_addFragment)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                navigateToAllMembers()
            }
            R.id.home -> {
                navigateToHome()
            }
            R.id.members -> {
                navigateToAllMembers()
            }
            R.id.attendances -> {
                navigateAllAttendances()
            }
            R.id.specific -> {
                navigateToSpecificAttendances()
            }
            R.id.account -> {
                navigateToMyAccount()
            }
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
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
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
import com.bethwelamkenya.church.databinding.ActivityMemberBinding
import com.bethwelamkenya.church.models.Member
import kotlin.system.exitProcess

class MemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberBinding
    private lateinit var navController: NavController
    private lateinit var navigationHostFragment: NavHostFragment
    private lateinit var member: ArrayList<Member>
    private var memberName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        member = intent.getSerializableExtra("member") as ArrayList<Member>
        memberName = intent.getStringExtra("member").toString()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navigationHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    private fun navigateToHome() {
        when (navController.currentDestination?.id){
            R.id.homeFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment3_self)
            }
            R.id.attendanceFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment2_to_homeFragment3)
            }
            R.id.accountFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment2_to_homeFragment3)
            }
        }
    }

    private fun navigateAllAttendances(){
        when (navController.currentDestination?.id){
            R.id.homeFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment3_to_attendanceFragment2)
            }
            R.id.attendanceFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment2_self)
            }
            R.id.accountFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment2_to_attendanceFragment2)
            }
        }
    }

    private fun navigateToMyAccount(){
        when (navController.currentDestination?.id){
            R.id.homeFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment3_to_accountFragment2)
            }
            R.id.attendanceFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_attendanceFragment2_to_accountFragment2)
            }
            R.id.accountFragmentMember -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_accountFragment2_self)
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
            R.id.home -> {
                navigateToHome()
            }
            R.id.account -> {
                navigateToMyAccount()
            }
            R.id.attendances -> {
                navigateAllAttendances()
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
package com.royalit.driverapp.Activitys

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.royalit.driverapp.Config.Preferences
import com.royalit.driverapp.Config.ViewController
import com.royalit.driverapp.Fragments.DeliveriesFragment
import com.royalit.driverapp.Fragments.HomeFragment
import com.royalit.driverapp.Fragments.ProfileFragment
import com.royalit.driverapp.R
import com.royalit.driverapp.databinding.ActivityDashBoardBinding
import java.util.Calendar

class DashBoardActivity : AppCompatActivity() {

    val binding: ActivityDashBoardBinding by lazy {
        ActivityDashBoardBinding.inflate(layoutInflater)
    }

    //exit
    private var isHomeFragmentDisplayed = false

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(
            this,
            ContextCompat.getColor(this, R.color.loginBg),
            false
        )

        //login
        Preferences.saveStringValue(applicationContext, Preferences.LOGINCHECK, "Login")



        inits()

    }

    private fun inits() {
        //default current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        // Format: yyyy-MM-dd
        val currentDate = String.format("%04d-%02d-%02d", year, month + 1, day)
        Preferences.saveStringValue(this@DashBoardActivity, Preferences.stateDate, currentDate)
        Preferences.saveStringValue(this@DashBoardActivity, Preferences.endDate, currentDate)

        //BottomNavigationView
        loadFragment(HomeFragment())
        bottomNavigationView = binding.navigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id. navigationHome-> {
                    ViewController.changeStatusBarColor(
                        this,
                        ContextCompat.getColor(this, R.color.loginBg),
                        false
                    )
                    loadFragment(HomeFragment())
                    true
                }
                R.id. navigationDeliveries-> {
                    ViewController.changeStatusBarColor(
                        this,
                        ContextCompat.getColor(this, R.color.colorPrimary),
                        false
                    )
                    loadFragment(DeliveriesFragment())
                    true
                }
                R.id.navigationProfile -> {
                    ViewController.changeStatusBarColor(
                        this,
                        ContextCompat.getColor(this, R.color.colorPrimary),
                        false
                    )
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, fragment)
        transaction.commit()
    }


    override fun onBackPressed() {
        exitDialog()
    }

    private fun exitDialog() {
        isHomeFragmentDisplayed = false
        val dialogBuilder = AlertDialog.Builder(this@DashBoardActivity)
        dialogBuilder.setTitle(R.string.Exit)
        dialogBuilder.setMessage(R.string.Areyousurewanttoexitthisapp)
        dialogBuilder.setPositiveButton(R.string.yes) { dialog, _ ->
            finishAffinity()
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        val b = dialogBuilder.create()
        b.show()
    }

}
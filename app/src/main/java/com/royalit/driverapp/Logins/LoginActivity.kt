package com.royalit.driverapp.Logins

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.royalit.driverapp.Activitys.DashBoardActivity
import com.royalit.driverapp.Config.ViewController
import com.royalit.driverapp.R
import com.royalit.driverapp.databinding.ActivityLoginBinding
import com.royalit.driverapp.databinding.ActivitySplashBinding

class LoginActivity : AppCompatActivity() {

    val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(this, ContextCompat.getColor(this, R.color.loginBg), false)

        inits()
    }


    private fun inits() {
        binding.linearLogin.setOnClickListener {
            if (binding.editId.text.toString() == "") {
                ViewController.showToast(this@LoginActivity,"Enter your Id")
            }else{
                if (binding.editId.text.toString() == "VillageVegetables2025") {
                    val intent = Intent(this@LoginActivity, DashBoardActivity::class.java)
                    startActivity(intent)
                }else{
                    ViewController.showToast(this@LoginActivity,"wrong ID")
                }
            }
        }

    }

}
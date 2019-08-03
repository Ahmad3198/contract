package com.example.contract.ui.activity.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.contract.ui.activity.main.MainActivity
import com.example.contract.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }, 2000
        )
    }
}

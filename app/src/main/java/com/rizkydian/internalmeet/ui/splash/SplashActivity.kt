package com.rizkydian.internalmeet.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.login.LoginActivity
import com.rizkydian.internalmeet.ui.main.MainActivity
import com.rizkydian.internalmeet.utils.SharedPrefs
import com.rizkydian.internalmeet.utils.USERNIP

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            if ((SharedPrefs.get(USERNIP, "") as String).isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 500L)
    }
}
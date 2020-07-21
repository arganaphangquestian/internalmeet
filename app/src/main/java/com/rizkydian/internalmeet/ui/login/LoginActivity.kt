package com.rizkydian.internalmeet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rizkydian.remindmeet.R
import com.rizkydian.remindmeet.databinding.ActivityLoginBinding
import com.rizkydian.remindmeet.ui.main.MainActivity
import com.rizkydian.remindmeet.utils.NetworkState
import com.rizkydian.remindmeet.utils.SharedPrefs
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        SharedPrefs.clearUser()
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginBinding.apply {
            lifecycleOwner = this@LoginActivity
            viewModel = loginViewModel
        }

        loginViewModel.loginStatus.observe(this, Observer {
            if(it.status == NetworkState.Status.SUCCESS) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else if(it.status == NetworkState.Status.FAILED) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
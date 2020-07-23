package com.rizkydian.internalmeet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.ActivityLoginBinding
import com.rizkydian.internalmeet.ui.main.MainActivity
import com.rizkydian.internalmeet.utils.NetworkState
import com.rizkydian.internalmeet.utils.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        SharedPrefs.clearUser()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
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
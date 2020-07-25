package com.rizkydian.internalmeet.ui.useradd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.ActivityUserAddBinding
import com.rizkydian.internalmeet.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_meet_add.*

@AndroidEntryPoint
class UserAddActivity : AppCompatActivity() {
    private lateinit var userAddBinding: ActivityUserAddBinding
    private lateinit var userAddViewModel: UserAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setTheme(R.style.AppThemeActionBar)
        supportActionBar?.title = "Add new User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userAddViewModel = ViewModelProvider(this).get(UserAddViewModel::class.java)
        userAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_add)
        userAddBinding.apply {
            lifecycleOwner = this@UserAddActivity
            viewModel = userAddViewModel
        }
        action()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun action() {
        mb_form.setOnClickListener {
            userAddViewModel.add().observe(this, Observer {
                if (it.status == NetworkState.Status.SUCCESS) {
                    finish()
                } else if (it.status == NetworkState.Status.FAILED) {
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
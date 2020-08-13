package com.rizkydian.internalmeet.ui.userdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.ActivityUserDetailBinding
import com.rizkydian.internalmeet.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_detail.*

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    private lateinit var userDetailBinding: ActivityUserDetailBinding
    private lateinit var userDetailViewModel: UserDetailViewModel

    companion object {
        var nip: String? = ""
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        nip = intent.getStringExtra("nip")
        setTheme(R.style.AppThemeActionBar)
        supportActionBar?.title = "User Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userDetailViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        userDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        userDetailBinding.apply {
            lifecycleOwner = this@UserDetailActivity
            viewModel = userDetailViewModel
        }

        load()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun load() {
        if (!nip.isNullOrEmpty()) {
            userDetailViewModel.load(nip!!).observe(this, Observer {
                when (it.status) {
                    NetworkState.Status.SUCCESS -> {
                        pb_detail.visibility = View.GONE
                        sv_detail.visibility = View.VISIBLE
                    }
                    NetworkState.Status.FAILED -> {
                        Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                        pb_detail.visibility = View.GONE
                        sv_detail.visibility = View.GONE
                    }
                    NetworkState.Status.RUNNING -> {
                        pb_detail.visibility = View.VISIBLE
                        sv_detail.visibility = View.GONE
                    }
                }
            })
        }
    }
}
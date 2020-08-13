package com.rizkydian.internalmeet.ui.useredit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.ActivityUserEditBinding
import com.rizkydian.internalmeet.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_edit.*

@AndroidEntryPoint
class UserEditActivity : AppCompatActivity() {
    private lateinit var userEditBinding: ActivityUserEditBinding
    private lateinit var userEditViewModel: UserEditViewModel

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
        userEditViewModel = ViewModelProvider(this).get(UserEditViewModel::class.java)
        userEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_edit)
        userEditBinding.apply {
            lifecycleOwner = this@UserEditActivity
            viewModel = userEditViewModel
        }

        load()
        action()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun load() {
        if (!nip.isNullOrEmpty()) {
            userEditViewModel.load(nip!!).observe(this, Observer { net ->
                if (net != null && net.status == NetworkState.Status.FAILED) {
                    Toast.makeText(this, net.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun action() {
        mb_form.setOnClickListener {
            userEditViewModel.edit(nip).observe(this, Observer {
                if (it.status == NetworkState.Status.SUCCESS) {
                    finish()
                } else if (it.status == NetworkState.Status.FAILED) {
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
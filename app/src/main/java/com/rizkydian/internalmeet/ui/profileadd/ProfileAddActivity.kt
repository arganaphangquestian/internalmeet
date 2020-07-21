package com.rizkydian.internalmeet.ui.profileadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.ActivityProfileAddBinding

class ProfileAddActivity : AppCompatActivity() {

    private lateinit var profileAddBinding: ActivityProfileAddBinding
    private lateinit var profileAddViewModel  : ProfileAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setTheme(R.style.AppThemeActionBar)
        supportActionBar?.title = "Add new User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        profileAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_add)
        profileAddBinding.apply {
            lifecycleOwner = this@ProfileAddActivity
            viewModel = profileAddViewModel
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
package com.rizkydian.internalmeet.ui.meetadd

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.ActivityMeetAddBinding
import com.rizkydian.internalmeet.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_meet_add.*

@AndroidEntryPoint
class MeetAddActivity : AppCompatActivity() {

    private lateinit var meetAddBinding: ActivityMeetAddBinding
    private lateinit var meetAddViewModel: MeetAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setTheme(R.style.AppThemeActionBar)
        supportActionBar?.title = "Add new Meet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        meetAddViewModel = ViewModelProvider(this).get(MeetAddViewModel::class.java)
        meetAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_meet_add)
        meetAddBinding.apply {
            lifecycleOwner = this@MeetAddActivity
            viewModel = meetAddViewModel
        }
        action()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun action() {
        mb_form.setOnClickListener {
            meetAddViewModel.add().observe(this, Observer {
                if (it.status == NetworkState.Status.SUCCESS) {
                    finish()
                } else if (it.status == NetworkState.Status.FAILED) {
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
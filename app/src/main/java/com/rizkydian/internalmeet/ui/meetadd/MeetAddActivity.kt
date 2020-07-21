package com.rizkydian.internalmeet.ui.meetadd

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rizkydian.remindmeet.R
import com.rizkydian.remindmeet.databinding.ActivityMeetAddBinding
import com.rizkydian.remindmeet.utils.ISFORM
import com.rizkydian.remindmeet.utils.NetworkState
import kotlinx.android.synthetic.main.activity_meet_add.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeetAddActivity : AppCompatActivity() {
    private var isForm = ISFORM[0]
    private lateinit var meetAddBinding: ActivityMeetAddBinding
    private val meetAddViewModel by viewModel<MeetAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setTheme(R.style.AppThemeActionBar)
        supportActionBar?.title = "Add new Meet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setPassData()
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

    private fun setPassData() {
        if (!intent.extras?.getString("id").isNullOrEmpty()) {
            isForm = ISFORM[1]
            meetAddViewModel.load(intent.extras!!.getString("id")!!)
            meetAddViewModel.isEdit(false)
            Toast.makeText(this, intent.extras!!.getString("id")!!, Toast.LENGTH_LONG).show()
        }
    }

    private fun action() {
        mb_form.setOnClickListener {
            if (isForm == 0) {
                meetAddViewModel.add().observe(this, Observer {
                    if (it.status == NetworkState.Status.SUCCESS) {
                        finish()
                    } else if (it.status == NetworkState.Status.FAILED) {
                        Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                    }
                })
            } else if (isForm == 1) {
                meetAddViewModel.edit(intent.getStringExtra("id")!!).observe(this, Observer {
                    if (it.status == NetworkState.Status.SUCCESS) {
                        finish()
                    } else if (it.status == NetworkState.Status.FAILED) {
                        Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
package com.rizkydian.internalmeet.ui.meetedit

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.data.Attendent
import com.rizkydian.internalmeet.databinding.ActivityMeetEditBinding
import com.rizkydian.internalmeet.utils.DATEFORMAT
import com.rizkydian.internalmeet.utils.NetworkState
import com.rizkydian.internalmeet.utils.TIMEFORMAT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_meet_edit.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MeetEditActivity : AppCompatActivity() {

    private lateinit var meetEditBinding: ActivityMeetEditBinding
    private lateinit var meetEditViewModel: MeetEditViewModel

    companion object {
        var id: String? = ""
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        id = intent.getStringExtra("id")
        setTheme(R.style.AppThemeActionBar)
        supportActionBar?.title = "Edit Meet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        meetEditViewModel = ViewModelProvider(this).get(MeetEditViewModel::class.java)
        meetEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_meet_edit)
        meetEditBinding.apply {
            lifecycleOwner = this@MeetEditActivity
            viewModel = meetEditViewModel
        }
        loadInitialMeet()
        action()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadInitialMeet() {
        meetEditViewModel.get(id).observe(this, Observer { net ->
            if (net != null && net.status == NetworkState.Status.FAILED) {
                Toast.makeText(this, net.msg, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun action() {
        mb_form.setOnClickListener {
            meetEditViewModel.edit(id).observe(this, Observer {
                if (it != null) {
                    if (it.status == NetworkState.Status.SUCCESS) {
                        finish()
                    } else if (it.status == NetworkState.Status.FAILED) {
                        Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        tie_date.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().build()
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnCancelListener {
                picker.dismiss()
            }
            picker.addOnNegativeButtonClickListener {
                picker.dismiss()
            }
            picker.addOnPositiveButtonClickListener {
                val date = SimpleDateFormat(DATEFORMAT, Locale.ROOT).format(it)
                meetEditViewModel.meetDate.value = date
                tie_date.setText(date)
                picker.dismiss()
            }
        }

        tie_time.setOnClickListener {
            val cal = Calendar.getInstance()
            val picker = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                val time = SimpleDateFormat(TIMEFORMAT, Locale.ROOT).format(cal.time)
                meetEditViewModel.meetTime.value = time
                tie_time.setText(time)
            }
            TimePickerDialog(
                this,
                picker,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        tie_participant.setOnClickListener {
            meetEditViewModel.users.observe(this, Observer {
                val attendents = arrayListOf<Attendent>()
                val multiItems = it.map { user -> user.name }.toTypedArray()
                val checkedItems = it.map { false }.toBooleanArray()
                MaterialAlertDialogBuilder(this)
                    .setTitle("Select Participants")
                    .setNeutralButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Submit") { dialog, _ ->
                        val users = it.filter { user ->
                            user.nip in attendents.map { attendent -> attendent.userNIP }
                        }
                        tie_participant.setText(users.joinToString { user -> user.name })
                        meetEditViewModel.meet.value?.participant = attendents
                        dialog.dismiss()
                    }
                    .setMultiChoiceItems(multiItems, checkedItems) { _, which, checked ->
                        if (checked) attendents.add(Attendent(it[which].nip, false, ""))
                    }
                    .show()
            })
        }
    }
}
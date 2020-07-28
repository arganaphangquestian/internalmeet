package com.rizkydian.internalmeet.ui.meetattendant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.meetattendant.absent.MeetAbsentFragment
import com.rizkydian.internalmeet.ui.meetattendant.present.MeetPresentFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_meet_attendent.*

@AndroidEntryPoint
class MeetAttendantActivity : AppCompatActivity() {

    private lateinit var meetAttendantAdapter: MeetAttendantAdapter

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
        supportActionBar?.title = "Detail Meet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_meet_attendent)
        meetAttendantAdapter = MeetAttendantAdapter(supportFragmentManager)
        meetAttendantAdapter.setData(
            listOf(
                FrameMeet(MeetPresentFragment.newInstance(), "Presents"),
                FrameMeet(MeetAbsentFragment.newInstance(), "Absents")
            )
        )
        vp_meetAttendent.adapter = meetAttendantAdapter
        tl_meetAttendent.setupWithViewPager(vp_meetAttendent, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    data class FrameMeet(
        val frag: Fragment,
        val title: String
    )
}
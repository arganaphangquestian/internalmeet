package com.rizkydian.internalmeet.ui.meetattendant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.meetattendant.absent.MeetAbsentFragment
import com.rizkydian.internalmeet.ui.meetattendant.present.MeetPresentFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_meet_attendent.*

@AndroidEntryPoint
class MeetAttendantActivity : AppCompatActivity() {

    private lateinit var meetAttendantAdapter : MeetAttendantAdapter
    private var id: String? = ""

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
                FrameMeet(MeetAbsentFragment.newInstance(), "Absents"),
                FrameMeet(MeetPresentFragment.newInstance(), "Presents")
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
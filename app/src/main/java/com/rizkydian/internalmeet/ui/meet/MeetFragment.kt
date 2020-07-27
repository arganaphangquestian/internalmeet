package com.rizkydian.internalmeet.ui.meet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.meet.history.MeetHistoryFragment
import com.rizkydian.internalmeet.ui.meet.upcoming.MeetUpcomingFragment
import com.rizkydian.internalmeet.ui.meetadd.MeetAddActivity
import com.rizkydian.internalmeet.utils.SharedPrefs
import com.rizkydian.internalmeet.utils.USERROLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_meet.*

@AndroidEntryPoint
class MeetFragment : Fragment() {

    companion object {
        fun newInstance() = MeetFragment()
    }

    private lateinit var meetAdapter: MeetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        meetAdapter = MeetAdapter(childFragmentManager)
        meetAdapter.setData(
            listOf(
                FrameMeet(MeetUpcomingFragment.newInstance(), "Up Coming"),
                FrameMeet(MeetHistoryFragment.newInstance(), "History")
            )
        )
        vp_meet.adapter = meetAdapter
        tl_meet.setupWithViewPager(vp_meet, true)
        action()
    }

    private fun action() {
        if(SharedPrefs.get(USERROLE, "") as String != "Admin") {
            fab_add.visibility = View.GONE
        }
        fab_add.setOnClickListener {
            startActivity(Intent(this.requireContext(), MeetAddActivity::class.java))
        }
    }

    data class FrameMeet(
        val frag: Fragment,
        val title: String
    )
}
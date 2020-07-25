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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_meet.*

@AndroidEntryPoint
class MeetFragment : Fragment() {

    companion object {
        fun newInstance() = MeetFragment()
    }

    private lateinit var meetViewModel: MeetViewModel
    private lateinit var meetAdapter: MeetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        meetViewModel = ViewModelProvider(this).get(MeetViewModel::class.java)
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
        fab_add.setOnClickListener {
            startActivity(Intent(this.requireContext(), MeetAddActivity::class.java))
        }
    }

    data class FrameMeet(
        val frag: Fragment,
        val title: String
    )
}
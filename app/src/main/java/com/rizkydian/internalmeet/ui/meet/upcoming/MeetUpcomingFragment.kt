package com.rizkydian.internalmeet.ui.meet.upcoming

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.meet.MeetItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_meet_upcoming.*

@AndroidEntryPoint
class MeetUpcomingFragment : Fragment() {

    companion object {
        fun newInstance() = MeetUpcomingFragment()
    }

    private lateinit var meetUpcomingViewModel: MeetUpcomingViewModel
    private lateinit var meetItemAdapter : MeetItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet_upcoming, container, false)
    }

    override fun onStart() {
        super.onStart()
        meetItemAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        meetItemAdapter.stopListening()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        meetUpcomingViewModel = ViewModelProvider(this).get(MeetUpcomingViewModel::class.java)
        meetItemAdapter = MeetItemAdapter(meetUpcomingViewModel.getMeets())
        meetItemAdapter.notifyDataSetChanged()
        rv_upcoming.apply {
            adapter = meetItemAdapter
            layoutManager = LinearLayoutManager(this@MeetUpcomingFragment.requireContext())
            setHasFixedSize(true)
        }
    }

}
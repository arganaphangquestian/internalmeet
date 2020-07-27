package com.rizkydian.internalmeet.ui.meetattendant.absent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.meetattendant.MeetAttendantActivity
import com.rizkydian.internalmeet.ui.meetattendant.MeetAttendantItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_meet_absent.*

@AndroidEntryPoint
class MeetAbsentFragment : Fragment() {

    companion object {
        fun newInstance() = MeetAbsentFragment()
    }

    private lateinit var meetAbsentViewModel: MeetAbsentViewModel
    private lateinit var meetItemAdapter: MeetAttendantItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet_absent, container, false)
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
        meetAbsentViewModel = ViewModelProvider(this).get(MeetAbsentViewModel::class.java)
        meetItemAdapter = MeetAttendantItemAdapter(meetAbsentViewModel.getUsers(MeetAttendantActivity.id ?: ""))
        meetItemAdapter.notifyDataSetChanged()
        rv_user.apply {
            adapter = meetItemAdapter
            layoutManager = LinearLayoutManager(this@MeetAbsentFragment.requireContext())
            setHasFixedSize(true)
        }
    }

}
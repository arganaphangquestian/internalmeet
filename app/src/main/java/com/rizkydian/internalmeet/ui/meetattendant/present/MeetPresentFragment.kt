package com.rizkydian.internalmeet.ui.meetattendant.present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.meet.MeetItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_meet_present.*

@AndroidEntryPoint
class MeetPresentFragment : Fragment() {

    companion object {
        fun newInstance() = MeetPresentFragment()
    }

    private lateinit var meetPresentViewModel: MeetPresentViewModel
    private lateinit var meetItemAdapter: MeetItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet_present, container, false)
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
        meetPresentViewModel = ViewModelProvider(this).get(MeetPresentViewModel::class.java)
        // TODO Please add get Users
        // meetItemAdapter = MeetItemAdapter(meetPresentViewModel.getUser())
        meetItemAdapter.notifyDataSetChanged()
        rv_user.apply {
            adapter = meetItemAdapter
            layoutManager = LinearLayoutManager(this@MeetPresentFragment.requireContext())
            setHasFixedSize(true)
        }
    }

}
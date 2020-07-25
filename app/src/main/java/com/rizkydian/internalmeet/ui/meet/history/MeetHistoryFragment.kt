package com.rizkydian.internalmeet.ui.meet.history

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
import kotlinx.android.synthetic.main.fragment_meet_history.*

@AndroidEntryPoint
class MeetHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = MeetHistoryFragment()
    }

    private lateinit var meetHistoryViewModel: MeetHistoryViewModel
    private lateinit var meetItemAdapter : MeetItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet_history, container, false)
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
        meetHistoryViewModel = ViewModelProvider(this).get(MeetHistoryViewModel::class.java)
        meetItemAdapter = MeetItemAdapter(meetHistoryViewModel.getMeets())
        meetItemAdapter.notifyDataSetChanged()
        rv_history.apply {
            adapter = meetItemAdapter
            layoutManager = LinearLayoutManager(this@MeetHistoryFragment.requireContext())
            setHasFixedSize(true)
        }
    }

}
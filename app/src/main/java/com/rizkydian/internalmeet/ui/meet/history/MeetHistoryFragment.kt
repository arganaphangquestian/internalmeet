package com.rizkydian.internalmeet.ui.meet.history

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.ui.meet.MeetItemAdapter
import com.rizkydian.internalmeet.ui.meetattendant.MeetAttendantActivity
import com.rizkydian.internalmeet.ui.meetdetail.MeetDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.card_meet.view.*
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
        action()
    }

    private fun action() {
        val intentDetail = Intent(this.requireContext(), MeetDetailActivity::class.java)
        val intentAttendent = Intent(this.requireContext(), MeetAttendantActivity::class.java)
        meetItemAdapter.setOnClickCallback(object : MeetItemAdapter.OnItemClickCallback {
            override fun onItemClicked(
                data: Meet,
                itemView: View
            ) {
                val popUp = PopupMenu(this@MeetHistoryFragment.requireContext(), itemView.root)
                popUp.inflate(R.menu.context_menu_meet)
                popUp.setOnMenuItemClickListener {
                    if(it.itemId == R.id.menu_context_detail) {
                        intentDetail.putExtra("id", data.id)
                        this@MeetHistoryFragment.requireActivity().startActivity(intentDetail)
                        return@setOnMenuItemClickListener true
                    } else if(it.itemId == R.id.menu_context_attendent) {
                        intentAttendent.putExtra("id", data.id)
                        this@MeetHistoryFragment.requireActivity().startActivity(intentAttendent)
                        return@setOnMenuItemClickListener true
                    }
                    return@setOnMenuItemClickListener false
                }
                popUp.show()
            }

        })
    }

}
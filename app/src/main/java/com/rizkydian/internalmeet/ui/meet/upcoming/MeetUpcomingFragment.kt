package com.rizkydian.internalmeet.ui.meet.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.ui.meet.MeetItemAdapter
import com.rizkydian.internalmeet.ui.meetattendant.MeetAttendantActivity
import com.rizkydian.internalmeet.ui.meetdetail.MeetDetailActivity
import com.rizkydian.internalmeet.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.card_meet.view.*
import kotlinx.android.synthetic.main.fragment_meet_upcoming.*

@AndroidEntryPoint
class MeetUpcomingFragment : Fragment() {

    companion object {
        fun newInstance() = MeetUpcomingFragment()
    }

    private lateinit var meetUpcomingViewModel: MeetUpcomingViewModel
    private lateinit var meetItemAdapter: MeetItemAdapter

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
                val popUp = PopupMenu(this@MeetUpcomingFragment.requireContext(), itemView.root)
                popUp.inflate(R.menu.context_menu_meet_upcoming)
                popUp.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_context_detail -> {
                            intentDetail.putExtra("id", data.id)
                            this@MeetUpcomingFragment.requireActivity().startActivity(intentDetail)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_context_attendent -> {
                            intentAttendent.putExtra("id", data.id)
                            this@MeetUpcomingFragment.requireActivity()
                                .startActivity(intentAttendent)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_context_done -> {
                            bottomSheet(data)
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                popUp.show()
            }

        })
    }

    private fun bottomSheet(data: Meet) {
        val bottomSheet =
            BottomSheetDialog(this@MeetUpcomingFragment.requireContext())
        bottomSheet.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        bottomSheet.setContentView(R.layout.bottom_meet_done)
        val result = bottomSheet.findViewById<TextInputEditText>(R.id.tie_result)
        val submitButton = bottomSheet.findViewById<MaterialButton>(R.id.mb_form)
        submitButton?.setOnClickListener {
            if (result != null && !result.text.isNullOrEmpty()) {
                meetUpcomingViewModel.setResultMeet(data.id, result.text.toString())
                    .observe(
                        this@MeetUpcomingFragment.viewLifecycleOwner,
                        Observer { net ->
                            if (net.status == NetworkState.Status.SUCCESS) {
                                Toast.makeText(bottomSheet.context, "Meet Done", Toast.LENGTH_LONG)
                                    .show()
                                bottomSheet.dismiss()
                            } else if (net.status == NetworkState.Status.FAILED) {
                                Toast.makeText(bottomSheet.context, net.msg, Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
            } else {
                Toast.makeText(
                    bottomSheet.context,
                    "Please fill Result Input",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        bottomSheet.show()
    }

}
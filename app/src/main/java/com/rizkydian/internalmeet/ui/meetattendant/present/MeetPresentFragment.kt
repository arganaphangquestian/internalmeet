package com.rizkydian.internalmeet.ui.meetattendant.present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.meetattendant.MeetAttendantActivity
import com.rizkydian.internalmeet.ui.meetattendant.MeetAttendantItemAdapter
import com.rizkydian.internalmeet.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_meet_present.*

@AndroidEntryPoint
class MeetPresentFragment : Fragment() {

    companion object {
        fun newInstance() = MeetPresentFragment()
    }

    private lateinit var meetPresentViewModel: MeetPresentViewModel
    private val meetItemAdapter = MeetAttendantItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet_present, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        meetPresentViewModel = ViewModelProvider(this).get(MeetPresentViewModel::class.java)
        meetPresentViewModel.getUsers(MeetAttendantActivity.id ?: "")
            .observe(viewLifecycleOwner, Observer {
                if (it.status == NetworkState.Status.SUCCESS) {
                    meetPresentViewModel.users.observe(viewLifecycleOwner, Observer { dataList ->
                        meetItemAdapter.setData(dataList)
                        if (it != null) {
                            rv_user.apply {
                                adapter = meetItemAdapter
                                layoutManager =
                                    LinearLayoutManager(this@MeetPresentFragment.requireContext())
                                setHasFixedSize(true)
                            }
                        }
                    })
                } else if (it.status == NetworkState.Status.FAILED) {
                    Toast.makeText(this.requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }

            })
    }

}
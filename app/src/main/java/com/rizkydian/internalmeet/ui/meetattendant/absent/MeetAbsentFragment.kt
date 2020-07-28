package com.rizkydian.internalmeet.ui.meetattendant.absent

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
import kotlinx.android.synthetic.main.fragment_meet_absent.*

@AndroidEntryPoint
class MeetAbsentFragment : Fragment() {

    companion object {
        fun newInstance() = MeetAbsentFragment()
    }

    private lateinit var meetAbsentViewModel: MeetAbsentViewModel
    private val meetItemAdapter = MeetAttendantItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet_absent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        meetAbsentViewModel = ViewModelProvider(this).get(MeetAbsentViewModel::class.java)
        meetAbsentViewModel.getUsers(MeetAttendantActivity.id ?: "")
            .observe(viewLifecycleOwner, Observer {
                if (it.status == NetworkState.Status.SUCCESS) {
                    meetAbsentViewModel.users.observe(viewLifecycleOwner, Observer { dataList ->
                        meetItemAdapter.setData(dataList)
                        if (it != null) {
                            rv_user.apply {
                                adapter = meetItemAdapter
                                layoutManager =
                                    LinearLayoutManager(this@MeetAbsentFragment.requireContext())
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
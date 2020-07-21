package com.rizkydian.internalmeet.ui.meet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.rizkydian.remindmeet.R
import com.rizkydian.remindmeet.data.Meet
import com.rizkydian.remindmeet.ui.meetadd.MeetAddActivity
import com.rizkydian.remindmeet.utils.NetworkState
import com.rizkydian.remindmeet.utils.SharedPrefs
import com.rizkydian.remindmeet.utils.USERROLE
import kotlinx.android.synthetic.main.fragment_meet.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeetFragment : Fragment() {

    companion object {
        fun newInstance() = MeetFragment()
    }

    private var tabActive = 0

    private val meetViewModel by viewModel<MeetViewModel>()

    private val pagers = listOf("Up Comming", "History")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
        action()
    }

    private fun load() {
        meetViewModel.meetStatus.observe(viewLifecycleOwner, Observer {
            srl_meet.isRefreshing = it.status == NetworkState.Status.RUNNING
        })
        meetViewModel.meets.observe(viewLifecycleOwner, Observer {
            if (it["undone"] != null && it["done"] != null) {
                println("ARGA $it")
                val meetAdapter = MeetAdapter()
                meetAdapter.setData(
                    listOf(
                        it["undone"] as List<Meet>,
                        it["done"] as List<Meet>
                    )
                )
                vp_meet.apply {
                    adapter = meetAdapter
                }
                if (it.size == 2) {
                    tab()
                }
            }
        })
    }

    private fun tab() {
        vp_meet.currentItem = tabActive
        vp_meet.isUserInputEnabled = true
        vp_meet.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabActive = position
            }
        })
        TabLayoutMediator(tl_meet, vp_meet) { tab, position ->
            tab.text =
                if (pagers[position].length > 20) pagers[position].substring(
                    0,
                    20 - 1
                ) + "..." else pagers[position]
        }.attach()
    }

    private fun action() {
        if(SharedPrefs.get(USERROLE, "") as String == "Admin") {
            fab_add.visibility = View.VISIBLE
        } else {
            fab_add.visibility = View.GONE
        }
        fab_add.setOnClickListener {
            startActivity(Intent(this.requireContext(), MeetAddActivity::class.java))
        }
        srl_meet.setOnRefreshListener {
            meetViewModel.refresh()
        }
    }

}
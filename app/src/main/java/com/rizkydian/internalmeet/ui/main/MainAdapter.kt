package com.rizkydian.internalmeet.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private lateinit var dataList: List<Fragment>

    fun setData(data: List<Fragment>) {
        dataList = data
    }

    override fun getItem(position: Int) = dataList[position]

    override fun getCount() = dataList.size
}
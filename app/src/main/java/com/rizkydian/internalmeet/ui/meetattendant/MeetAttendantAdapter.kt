package com.rizkydian.internalmeet.ui.meetattendant

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MeetAttendantAdapter (supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private lateinit var dataList : List<MeetAttendantActivity.FrameMeet>

    fun setData(data: List<MeetAttendantActivity.FrameMeet>) {
        dataList = data
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return dataList[position].title
    }

    override fun getItem(position: Int) = dataList[position].frag

    override fun getCount() = dataList.size

}
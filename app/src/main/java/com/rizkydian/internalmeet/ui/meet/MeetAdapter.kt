package com.rizkydian.internalmeet.ui.meet

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MeetAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private lateinit var dataList : List<MeetFragment.FrameMeet>

    fun setData(data: List<MeetFragment.FrameMeet>) {
        dataList = data
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return dataList[position].title
    }

    override fun getItem(position: Int) = dataList[position].frag

    override fun getCount() = dataList.size

}
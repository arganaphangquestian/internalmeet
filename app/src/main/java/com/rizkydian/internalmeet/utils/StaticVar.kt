package com.rizkydian.internalmeet.utils

import com.rizkydian.internalmeet.ui.home.HomeFragment
import com.rizkydian.internalmeet.ui.meet.MeetFragment
import com.rizkydian.internalmeet.ui.profile.ProfileFragment
import com.rizkydian.internalmeet.ui.users.UserFragment

val MEMBERBOTTOMMENU = arrayListOf(
    HomeFragment.newInstance(),
    MeetFragment.newInstance(),
    ProfileFragment.newInstance()
)

val ADMINBOTTOMMENU = arrayListOf(
    HomeFragment.newInstance(),
    MeetFragment.newInstance(),
    UserFragment.newInstance(),
    ProfileFragment.newInstance()
)

const val USERNIP = "USERNIP"
const val USERROLE = "USERROLE"
val ISFORM = arrayListOf(0, 1, 2) // 0 -> Create, 1 -> Read, 2 -> Edit
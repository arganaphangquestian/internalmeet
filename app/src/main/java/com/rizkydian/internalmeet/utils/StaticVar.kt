package com.rizkydian.internalmeet.utils

import android.content.res.Resources
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

val POSITION = arrayOf(
    "Keuangan",
    "Kepegawaian",
    "Kepala Cabang Dinas",
    "Kepala Seksi SMA",
    "Kepala Seksi SMK",
    "Kepala Sekolah"
)

const val USERNIP = "USERNIP"
const val USERROLE = "USERROLE"

const val DATETIMEFORMAT = "dd/M/yyyy hh:mm:ss"
const val DATEFORMAT = "dd/M/yyyy"
const val TIMEFORMAT = "hh:mm:ss"

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}
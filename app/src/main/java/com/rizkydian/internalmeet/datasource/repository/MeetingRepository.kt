package com.rizkydian.internalmeet.datasource.repository

import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.network.MeetingNetwork
import javax.inject.Inject

class MeetingRepository @Inject constructor(private val network: MeetingNetwork) {
    fun meets() = network.meets()
    fun add(meet: Meet) = network.add(meet)
    fun edit(id: String, meet: Meet) = network.edit(id, meet)
    fun broadcast(to: String, title: String) = network.broadcast(to, title)
    fun getByID(id: String) = network.getByID(id)
    fun getByMeetID(id: String) = network.getByMeetID(id)
}
package com.rizkydian.internalmeet.datasource.repository

import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.network.MeetingNetwork

class MeetingRepository (private val network: MeetingNetwork) {
    fun meets() = network.meets()
    fun add(meet: Meet) = network.add(meet)
    fun edit(id: String, meet: Meet) = network.edit(id, meet)
}
package com.rizkydian.internalmeet.datasource.network

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rizkydian.internalmeet.data.AttendentBody
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.data.Notif
import com.rizkydian.internalmeet.service.NotificationService
import javax.inject.Inject

class MeetingNetwork @Inject constructor(private val notificationService: NotificationService) {
    private val firestore = Firebase.firestore

    fun meets() = firestore.collection("meetings")

    fun add(meet: Meet) = firestore.collection("meetings").add(meet)
    fun edit(id: String, meet: Meet) = firestore.collection("meetings").document(id).set(meet)
    fun getByID(id: String) = firestore.collection("meetings").document(id)
    fun getByMeetID(id: String) = firestore.collection("meetings").whereEqualTo("id", id)

    fun broadcast(to: String, title: String) = notificationService.pushNotification(
        AttendentBody(
            data = Notif(
                title = title,
                body = "Please join $title meeting"
            ), notification = Notif(title = title, body = "Please join $title meeting"), to = to
        )
    )
}
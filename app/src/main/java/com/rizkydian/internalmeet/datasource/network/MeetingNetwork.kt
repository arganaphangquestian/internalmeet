package com.rizkydian.internalmeet.datasource.network

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rizkydian.internalmeet.data.Meet
import javax.inject.Inject

class MeetingNetwork @Inject constructor() {
    private val firestore = Firebase.firestore

    fun meets() = firestore.collection("meetings")

    fun add(meet: Meet) = firestore.collection("meetings").add(meet)
    fun edit(id: String, meet: Meet) = firestore.collection("meetings").document(id).set(meet)
}
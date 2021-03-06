package com.rizkydian.internalmeet.datasource.network

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rizkydian.internalmeet.data.User
import javax.inject.Inject

class UserNetwork @Inject constructor() {

    private val firestore = Firebase.firestore

    fun login(nip: String, password: String) = firestore
        .collection("users")
        .whereEqualTo("nip", nip)
        .whereEqualTo("password", password)

    fun register(user: User) = firestore
        .collection("users")
        .add(
            hashMapOf(
                "nip" to user.nip,
                "name" to user.name,
                "email" to user.email,
                "password" to user.password,
                "imageProfile" to user.imageProfile,
                "position" to user.position,
                "role" to user.role
            )
        )

    fun getUsers() = firestore.collection("users")

    fun getUser(nip: String) = firestore
        .collection("users")
        .whereEqualTo("nip", nip)

    fun getUserByPosition(position: String) = firestore
        .collection("users")
        .whereEqualTo("position", position)

    fun getUserByID(id: String) = firestore
        .collection("users")
        .document(id)
}
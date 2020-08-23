package com.rizkydian.internalmeet.datasource.repository

import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.network.UserNetwork
import javax.inject.Inject

class UserRepository @Inject constructor (private val network: UserNetwork) {
    fun login(nip: String, password: String) = network.login(nip, password)

    fun register(user: User) = network.register(user)

    fun getUsers() = network.getUsers()

    fun getUser(nip: String) = network.getUser(nip)

    fun getUserByID(id: String) = network.getUserByID(id)

    fun getUserByPosition(position: String) = network.getUserByPosition(position)

}
package com.rizkydian.internalmeet.ui.useradd

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.UserRepository
import com.rizkydian.internalmeet.utils.NetworkState

class UserAddViewModel @ViewModelInject constructor(private val userRepo: UserRepository): ViewModel() {
    val ROLES = arrayOf("Admin", "Member")

    val user = MutableLiveData(User())
    private val networkState = MutableLiveData<NetworkState>()

    fun add(): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING
        if (
            !user.value?.nip.isNullOrEmpty() &&
            !user.value?.name.isNullOrEmpty() &&
            !user.value?.email.isNullOrEmpty() &&
            !user.value?.password.isNullOrEmpty() &&
            !user.value?.position.isNullOrEmpty() &&
            !user.value?.role.isNullOrEmpty()
        ) {
            val tmpUser =
                User(
                    nip = user.value!!.nip,
                    name = user.value!!.name,
                    email = user.value!!.email,
                    password = user.value!!.password,
                    imageProfile = user.value!!.imageProfile,
                    position = user.value!!.position,
                    role = user.value!!.role
                )
            println(tmpUser)
            userRepo.register(tmpUser).addOnSuccessListener {
                networkState.value = NetworkState.LOADED
            }.addOnFailureListener {
                networkState.value = NetworkState.error(it.message)
            }
        } else {
            networkState.value = NetworkState.error("Please fill require field")
        }
        return networkState
    }
}
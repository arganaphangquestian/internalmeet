package com.rizkydian.internalmeet.ui.userdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.UserRepository
import com.rizkydian.internalmeet.utils.NetworkState

class UserDetailViewModel @ViewModelInject constructor(private val userRepo: UserRepository) : ViewModel() {
    val user = MutableLiveData(User())
    private val networkState = MutableLiveData<NetworkState>()

    fun load(nip: String): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING
        println("ARGANA =? $nip")
        userRepo.getUser(nip).get()
            .addOnSuccessListener {
                if(it.documents.isNullOrEmpty()) {
                    networkState.value = NetworkState.error("User Not Found!")
                } else {
                    user.value = it.documents[0].toObject<User>()
                    networkState.value = NetworkState.LOADED
                }

            }
            .addOnFailureListener {
                networkState.value = NetworkState.error(it.message)
            }

        return networkState
    }
}
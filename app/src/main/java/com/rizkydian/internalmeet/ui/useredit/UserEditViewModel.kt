package com.rizkydian.internalmeet.ui.useredit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.UserRepository
import com.rizkydian.internalmeet.utils.NetworkState

class UserEditViewModel @ViewModelInject constructor(private val userRepo: UserRepository) :
    ViewModel() {
    val ROLES = arrayOf("Admin", "Member")
    val GENDER = arrayOf("Laki-Laki", "Perempuan")
    val user = MutableLiveData(User())
    private val networkState = MutableLiveData<NetworkState>()

    fun load(nip: String): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING

        userRepo.getUser(nip).get()
            .addOnSuccessListener {
                if (it.documents.isNullOrEmpty()) {
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

    fun edit(nip: String?): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING
        if (
            !user.value?.name.isNullOrEmpty() &&
            !user.value?.email.isNullOrEmpty() &&
            !user.value?.password.isNullOrEmpty() &&
            !user.value?.position.isNullOrEmpty() &&
            !user.value?.role.isNullOrEmpty() &&
            !user.value?.gender.isNullOrEmpty()
        ) {
            userRepo.getUser(nip ?: "").get().addOnSuccessListener { doc ->
                if (doc.documents[0].toObject<User>() != null) {
                    userRepo.getUserByID(doc.documentChanges[0].document.id).update(
                        mapOf(
                            "name" to user.value!!.name,
                            "email" to user.value!!.email,
                            "password" to user.value!!.password,
                            "imageProfile" to user.value!!.imageProfile,
                            "position" to user.value!!.position,
                            "role" to user.value!!.role,
                            "gender" to user.value!!.gender
                        )
                    ).addOnSuccessListener {
                        networkState.value = NetworkState.LOADED
                    }.addOnFailureListener {
                        networkState.value = NetworkState.error(it.message)
                    }
                }
            }
        } else {
            networkState.value = NetworkState.error("Please fill require field")
        }
        return networkState
    }
}
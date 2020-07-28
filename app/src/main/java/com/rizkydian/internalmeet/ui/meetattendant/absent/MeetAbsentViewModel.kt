package com.rizkydian.internalmeet.ui.meetattendant.absent

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository
import com.rizkydian.internalmeet.datasource.repository.UserRepository
import com.rizkydian.internalmeet.utils.NetworkState
import timber.log.Timber

class MeetAbsentViewModel @ViewModelInject constructor(
    private val meetingRepository: MeetingRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    val users = MutableLiveData<List<User>>()
    private val networkState = MutableLiveData<NetworkState>()

    fun getUsers(id: String): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING
        meetingRepository.getByMeetID(id)
            .get().addOnSuccessListener {
                @Suppress("UNCHECKED_CAST")
                if (it.documents.isNotEmpty()) {
                    val userNips = mutableListOf<String>()
                    (it.documents[0].get("participant") as List<HashMap<String, Any>>).map { nips ->
                        if (!(nips["attendent"] as Boolean)) {
                            userNips.add(nips["userNIP"] as String)
                        }
                    }
                    if (!userNips.isNullOrEmpty()) {
                        userRepo.getUsers()
                            .whereIn("nip", userNips.map { nip -> nip }).get()
                            .addOnSuccessListener { qSnap ->
                                users.value = qSnap.toObjects(User::class.java)
                                networkState.value = NetworkState.LOADED
                            }
                            .addOnFailureListener { e ->
                                networkState.value = NetworkState.error(e.message)
                                Timber.e(e)
                            }
                    }
                } else {
                    networkState.value = NetworkState.error("No Data Users Here")
                }
            }.addOnFailureListener {
                networkState.value = NetworkState.error(it.message)
                Timber.e(it)
            }
        return networkState
    }
}
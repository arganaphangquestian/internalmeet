package com.rizkydian.internalmeet.ui.meetdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository
import com.rizkydian.internalmeet.utils.NetworkState

class MeetDetailViewModel @ViewModelInject constructor(private val meetRepo: MeetingRepository) :
    ViewModel() {
    val meet = MutableLiveData(Meet())
    private val networkState = MutableLiveData<NetworkState>()

    fun load(id: String): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING

        meetRepo.getByMeetID(id).get()
            .addOnSuccessListener {
                meet.value = it.documents[0].toObject<Meet>()
                networkState.value = NetworkState.LOADED

            }
            .addOnFailureListener {
                networkState.value = NetworkState.error(it.message)

            }

        return networkState
    }
}
package com.rizkydian.internalmeet.ui.meet.upcoming

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.toObject
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository
import com.rizkydian.internalmeet.utils.NetworkState

class MeetUpcomingViewModel @ViewModelInject constructor(private val meetRepository: MeetingRepository) :
    ViewModel() {
    private val networkState = MutableLiveData<NetworkState>()

    fun getMeets() = FirestoreRecyclerOptions.Builder<Meet>()
        .setQuery(meetRepository.meets().whereEqualTo("done", false), Meet::class.java).build()

    fun setResultMeet(id: String, result: String): MutableLiveData<NetworkState> {
        meetRepository.getByMeetID(id).get().addOnSuccessListener { documentSnapshots ->
            if (documentSnapshots.documents[0].toObject<Meet>() != null) {
                meetRepository.getByID(documentSnapshots.documentChanges[0].document.id)
                    .update(mapOf(
                        "result" to result,
                        "done" to true
                    ))
                    .addOnSuccessListener {
                        networkState.value = NetworkState.LOADED
                    }
                    .addOnFailureListener {
                        networkState.value = NetworkState.error(it.message)
                    }
            } else {
                networkState.value = NetworkState.error("Meet Not Found")
            }
        }.addOnFailureListener {
            networkState.value = NetworkState.error(it.message)
        }
        return networkState
    }
}
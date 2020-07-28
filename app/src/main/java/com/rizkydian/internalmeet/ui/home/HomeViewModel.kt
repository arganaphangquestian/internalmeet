package com.rizkydian.internalmeet.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository
import com.rizkydian.internalmeet.utils.DATETIMEFORMAT
import com.rizkydian.internalmeet.utils.NetworkState
import com.rizkydian.internalmeet.utils.SharedPrefs
import com.rizkydian.internalmeet.utils.USERNIP
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel @ViewModelInject constructor(private val meetRepo: MeetingRepository) :
    ViewModel() {
    private val networkState = MutableLiveData<NetworkState>()

    fun attendent(id: String): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING
        meetRepo.getByMeetID(id).get().addOnSuccessListener { documentSnapshots ->
            val meet = documentSnapshots.documents[0].toObject<Meet>()
            if (meet != null) {
                meet.participant?.forEach {
                    if (it.userNIP == SharedPrefs.get(USERNIP, "")) {
                        it.attendent = true
                        it.timeAttendent =
                            SimpleDateFormat(DATETIMEFORMAT, Locale.ROOT).format(Date())
                    } else {
                        networkState.value = NetworkState.error("User not participant")
                    }
                }
                meetRepo.getByID(documentSnapshots.documentChanges[0].document.id)
                    .update("participant", meet.participant)
                    .addOnSuccessListener {
                        networkState.value = NetworkState.LOADED
                    }
                    .addOnFailureListener {
                        networkState.value = NetworkState.error(it.message)
                    }
            } else {
                networkState.value = NetworkState.error("QR incorrect")
            }
        }.addOnFailureListener {
            networkState.value = NetworkState.error(it.message)
        }

        return networkState
    }
}
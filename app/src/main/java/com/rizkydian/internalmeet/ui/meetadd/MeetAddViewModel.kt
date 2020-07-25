package com.rizkydian.internalmeet.ui.meetadd

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository
import com.rizkydian.internalmeet.utils.NetworkState

class MeetAddViewModel @ViewModelInject constructor(private val meetRepo: MeetingRepository) :
    ViewModel() {
    val meet = MutableLiveData(Meet())
    private val networkState = MutableLiveData<NetworkState>()

    fun add(): MutableLiveData<NetworkState> {
        networkState.value = null
        if (
            !meet.value?.name.isNullOrEmpty() &&
            !meet.value?.datetime.isNullOrEmpty() &&
            !meet.value?.place.isNullOrEmpty() &&
            !meet.value?.topic.isNullOrEmpty() &&
            !meet.value?.participant.isNullOrEmpty()
        ) {
            networkState.value = NetworkState.LOADING
            meetRepo.add(
                Meet(
                    name = meet.value!!.name,
                    datetime = meet.value!!.datetime,
                    place = meet.value!!.place,
                    topic = meet.value!!.topic,
                    note = meet.value!!.note,
                    participant = meet.value!!.participant,
                    result = meet.value!!.result,
                    done = meet.value!!.done
                )
            ).addOnSuccessListener {
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
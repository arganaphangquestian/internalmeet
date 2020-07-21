package com.rizkydian.internalmeet.ui.meetadd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.remindmeet.data.Meet
import com.rizkydian.remindmeet.datasource.repository.MeetingRepository
import com.rizkydian.remindmeet.utils.NetworkState

class MeetAddViewModel(private val meetingRepository: MeetingRepository) : ViewModel() {
    val isEditForm = MutableLiveData(true)
    private val networkState = MutableLiveData<NetworkState>()

    val name = MutableLiveData("")
    val datetime = MutableLiveData("")
    val place = MutableLiveData("")
    val topic = MutableLiveData("")
    val note = MutableLiveData("")
    val participant = MutableLiveData("")
    val result = MutableLiveData("")
    val isDone = MutableLiveData(false)

    fun load(id: String) {
        networkState.value = NetworkState.LOADING
        meetingRepository.meets().document(id).addSnapshotListener { snapshot, exception ->
            if (snapshot != null) {
                name.value = snapshot["name"] as String
                datetime.value = snapshot["datetime"] as String
                place.value = snapshot["place"] as String
                topic.value = snapshot["topic"] as String
                note.value = snapshot["note"] as String
                participant.value = snapshot["participant"] as String
                result.value = snapshot["result"] as String
            }
            if (exception != null) {
                networkState.value = NetworkState.error(exception.message)
            }
        }
    }

    fun add(): MutableLiveData<NetworkState> {
        networkState.value = null
        if (
            name.value.toString().isNotEmpty() &&
            datetime.value.toString().isNotEmpty() &&
            place.value.toString().isNotEmpty() &&
            topic.value.toString().isNotEmpty() &&
            participant.value.toString().isNotEmpty()
        ) {
            networkState.value = NetworkState.LOADING
            meetingRepository.add(
                Meet(
                    name = name.value.toString(),
                    datetime = datetime.value.toString(),
                    place = place.value.toString(),
                    topic = topic.value.toString(),
                    note = note.value.toString(),
                    participant = participant.value.toString(),
                    result = result.value.toString(),
                    done = isDone.value ?: false
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

    fun edit(id: String): MutableLiveData<NetworkState> {
        networkState.value = null
        if (
            name.value.toString().isNotEmpty() &&
            datetime.value.toString().isNotEmpty() &&
            place.value.toString().isNotEmpty() &&
            topic.value.toString().isNotEmpty() &&
            participant.value.toString().isNotEmpty()
        ) {
            networkState.value = NetworkState.LOADING
            meetingRepository.edit(
                id = id,
                meet = Meet(
                    name = name.value.toString(),
                    datetime = datetime.value.toString(),
                    place = place.value.toString(),
                    topic = topic.value.toString(),
                    note = note.value.toString(),
                    participant = participant.value.toString(),
                    result = result.value.toString(),
                    done = isDone.value ?: false
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

    fun isEdit(bool: Boolean) {
        isEditForm.value = bool
    }
}
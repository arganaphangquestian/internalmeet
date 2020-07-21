package com.rizkydian.internalmeet.ui.meet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.rizkydian.remindmeet.data.Meet
import com.rizkydian.remindmeet.datasource.repository.MeetingRepository
import com.rizkydian.remindmeet.utils.NetworkState

@Suppress("UNCHECKED_CAST")
class MeetViewModel(private val meetingRepository: MeetingRepository) : ViewModel() {
    // val meets = MutableLiveData<MutableMap<String, List<Meet>>>() // Check By Role if Admin get All, if Member get By User
    val meets = MutableLiveData<MutableMap<String, List<Meet>>>()
    val meetStatus = MutableLiveData<NetworkState>()

    init {
        refresh()
    }

    fun refresh() {
        meetStatus.value = NetworkState.LOADING
        val data = mutableMapOf<String, List<Meet>>()

        meetingRepository.meets()
            .addSnapshotListener { querySnapshot, e ->
                val resDataDone = mutableListOf<Meet>()
                val resDataUndone = mutableListOf<Meet>()
                if (querySnapshot != null) {
                    for (res in querySnapshot.documentChanges) {
                        if (res.type == DocumentChange.Type.MODIFIED) {
                            refresh()
                        }
                        if(res.document["done"] as Boolean) {
                            resDataDone.add(res.document.toObject(Meet::class.java))
                        } else {
                            resDataUndone.add(res.document.toObject(Meet::class.java))
                        }
                    }
                    meetStatus.value = NetworkState.LOADED
                }
                if(e != null) {
                    meetStatus.value = NetworkState.error(e.message)
                } else {
                    data["undone"] = resDataUndone
                    data["done"] = resDataDone
                    meets.value = data
                }
            }
    }
}
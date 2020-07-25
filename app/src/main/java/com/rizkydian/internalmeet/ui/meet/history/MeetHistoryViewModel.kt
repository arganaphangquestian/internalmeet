package com.rizkydian.internalmeet.ui.meet.history

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository

class MeetHistoryViewModel @ViewModelInject constructor(private val meetRepository: MeetingRepository) : ViewModel() {
    fun getMeets() = FirestoreRecyclerOptions.Builder<Meet>()
        .setQuery(meetRepository.meets().whereEqualTo("done", true), Meet::class.java).build()
}
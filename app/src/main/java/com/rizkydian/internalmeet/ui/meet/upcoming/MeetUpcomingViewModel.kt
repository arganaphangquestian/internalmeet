package com.rizkydian.internalmeet.ui.meet.upcoming

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository

class MeetUpcomingViewModel @ViewModelInject constructor(private val meetRepository: MeetingRepository) : ViewModel() {
    fun getMeets() = FirestoreRecyclerOptions.Builder<Meet>()
        .setQuery(meetRepository.meets().whereEqualTo("done", false), Meet::class.java).build()
}
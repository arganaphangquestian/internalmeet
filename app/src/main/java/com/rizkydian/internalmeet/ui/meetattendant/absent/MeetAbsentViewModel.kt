package com.rizkydian.internalmeet.ui.meetattendant.absent

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rizkydian.internalmeet.data.Attendent
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository
import com.rizkydian.internalmeet.datasource.repository.UserRepository

class MeetAbsentViewModel @ViewModelInject constructor(
    private val meetingRepository: MeetingRepository,
    private val userRepo: UserRepository
) : ViewModel() {
    fun getUsers(id: String): FirestoreRecyclerOptions<User> {
        val userNips = meetingRepository.getByMeetID(id).whereEqualTo("attendent", true)
            .get().result?.documents?.get(0)
            ?.get("participant") as? List<Attendent>

        return FirestoreRecyclerOptions.Builder<User>()
            .setQuery(
                userRepo.getUsers().whereIn("nip", userNips?.map { it.userNIP } ?: listOf())
                    .whereEqualTo("attendent", false),
                User::class.java
            ).build()
    }
}
package com.rizkydian.internalmeet.ui.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.UserRepository

class UserViewModel @ViewModelInject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    fun getUsers() = FirestoreRecyclerOptions.Builder<User>()
        .setQuery(userRepository.getUsers(), User::class.java).build()
}
package com.rizkydian.internalmeet.ui.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.UserRepository

class UserViewModel @ViewModelInject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val position = MutableLiveData<String>("")
    val POSITION = arrayOf(
        "Keuangan",
        "Kepegawaian",
        "Kepala Cabang Dinas",
        "Kepala Seksi SMA",
        "Kepala Seksi SMK",
        "Kepala Sekolah"
    )

    fun getUsers() = FirestoreRecyclerOptions.Builder<User>()
        .setQuery(userRepository.getUsers(), User::class.java).build()

    fun getUsersFilter(position: String): FirestoreRecyclerOptions<User> {
        if(position == "") {
            return getUsers()
        }
        return FirestoreRecyclerOptions.Builder<User>()
        .setQuery(userRepository.getUserByPosition(position), User::class.java).build()
    }
}
package com.rizkydian.internalmeet.ui.profileadd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.remindmeet.datasource.repository.UserRepository

class ProfileAddViewModel(private val userRepository: UserRepository) : ViewModel() {
    var isEditForm = MutableLiveData(true)
    var nip = MutableLiveData("")
    var name = MutableLiveData("")
    var email = MutableLiveData("")
    var password = MutableLiveData("")
    var imageProfile = MutableLiveData("")
    var position = MutableLiveData("")
    var role = MutableLiveData("")

    val ROLES = arrayOf("Admin", "Member")

    fun add() {

    }

    fun edit() {

    }
}
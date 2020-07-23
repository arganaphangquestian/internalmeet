package com.rizkydian.internalmeet.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.internalmeet.datasource.repository.UserRepository
import com.rizkydian.internalmeet.utils.SharedPrefs
import com.rizkydian.internalmeet.utils.USERNIP

class ProfileViewModel @ViewModelInject constructor(userRepository: UserRepository) :
    ViewModel() {
    val nip = MutableLiveData(SharedPrefs.get(USERNIP, "") as String)
    val name = MutableLiveData("")

    init {
        userRepository.getUser(SharedPrefs.get(USERNIP, "") as String).get()
            .addOnSuccessListener { result ->
                if (result.documents.size > 0) {
                    name.value = result.documents[0].data?.get("name") as String
                }
            }
            .addOnFailureListener {
                name.value = ""
            }
    }
}
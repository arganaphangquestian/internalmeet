package com.rizkydian.internalmeet.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.remindmeet.datasource.repository.UserRepository
import com.rizkydian.remindmeet.utils.NetworkState
import com.rizkydian.remindmeet.utils.SharedPrefs
import com.rizkydian.remindmeet.utils.USERNIP
import com.rizkydian.remindmeet.utils.USERROLE

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    val nip = MutableLiveData("")
    val password = MutableLiveData("")
    val loginStatus = MutableLiveData<NetworkState>()

    fun login() {
        if (loginStatus.value?.status != NetworkState.Status.RUNNING) {
            loginStatus.value = NetworkState.LOADING
            if (!nip.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
                val res = userRepository.login(nip.value!!, password.value!!)
                res.get()
                    .addOnSuccessListener { result ->
                        if (result.documents.size > 0) {
                            SharedPrefs.run {
                                set(USERNIP, result.documents[0].data?.get("nip") as String)
                                set(USERROLE, result.documents[0].data?.get("role") as String)
                            }
                            loginStatus.value = NetworkState.LOADED
                        } else {
                            loginStatus.value = NetworkState.error("User not found")
                        }
                    }
                    .addOnFailureListener { e ->
                        loginStatus.value = NetworkState.error("Firebase Err : $e")
                    }
            } else {
                loginStatus.value = NetworkState.error("NIP or Password can't Blank")
            }
        }
    }
}
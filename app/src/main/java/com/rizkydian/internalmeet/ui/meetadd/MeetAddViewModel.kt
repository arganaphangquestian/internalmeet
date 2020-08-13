package com.rizkydian.internalmeet.ui.meetadd

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkydian.internalmeet.data.AttendentResponse
import com.rizkydian.internalmeet.data.Meet
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository
import com.rizkydian.internalmeet.datasource.repository.UserRepository
import com.rizkydian.internalmeet.utils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MeetAddViewModel @ViewModelInject constructor(
    private val meetRepo: MeetingRepository,
    userRepo: UserRepository
) :
    ViewModel() {

    val meet = MutableLiveData(Meet())
    val meetDate = MutableLiveData("")
    val meetTime = MutableLiveData("")
    val users = MutableLiveData<List<User>>()
    val networkState = MutableLiveData<NetworkState>()

    init {
        networkState.value = NetworkState.LOADING
        userRepo.getUsers()
            .get()
            .addOnSuccessListener { documents ->
                val tmpUsers = mutableListOf<User>()
                for (document in documents) {
                    val resUser = User(
                        name = document.get("name") as String,
                        nip = document.get("nip") as String,
                        token = document.get("token") as? String
                    )
                    tmpUsers.add(resUser)
                }
                users.value = tmpUsers
                networkState.value = NetworkState.LOADED
            }
            .addOnFailureListener {
                networkState.value = NetworkState.error(it.message)
            }
    }

    fun add(): MutableLiveData<NetworkState> {
        networkState.value = null
        if (
            !meet.value?.name.isNullOrEmpty() &&
            !meet.value?.place.isNullOrEmpty() &&
            !meet.value?.topic.isNullOrEmpty() &&
            !meet.value?.participant.isNullOrEmpty() &&
            !meetDate.value.isNullOrEmpty() &&
            !meetTime.value.isNullOrEmpty()
        ) {
            networkState.value = NetworkState.LOADING
            meetRepo.add(
                Meet(
                    id = meetRepo.meets().document().id,
                    name = meet.value!!.name,
                    datetime = "${meetDate.value!!} ${meetTime.value!!}",
                    place = meet.value!!.place,
                    topic = meet.value!!.topic,
                    note = meet.value!!.note,
                    participant = meet.value!!.participant,
                    result = meet.value!!.result,
                    done = meet.value!!.done
                )
            ).addOnSuccessListener {
                var flag = 0
                meet.value!!.participant?.map {
                    users.value!!.filter { user ->
                        user.nip == it.userNIP
                    }.forEach { user ->
                        if (!user.token.isNullOrEmpty()) {
                            meetRepo.broadcast(user.token!!, meet.value?.name ?: "")
                                .enqueue(object :
                                    Callback<AttendentResponse> {
                                    override fun onFailure(
                                        call: Call<AttendentResponse>,
                                        t: Throwable
                                    ) {
                                        Timber.e(t)
                                        networkState.value = NetworkState.error(t.message)
                                    }

                                    override fun onResponse(
                                        call: Call<AttendentResponse>,
                                        response: Response<AttendentResponse>
                                    ) {
                                        println(response.body())
                                        flag++
                                        if (flag == meet.value!!.participant?.size) {
                                            networkState.value = NetworkState.LOADED
                                        }
                                    }
                                })
                        }
                    }
                }
            }.addOnFailureListener {
                networkState.value = NetworkState.error(it.message)
            }
        } else {
            networkState.value = NetworkState.error("Please fill require field")
        }
        return networkState
    }
}
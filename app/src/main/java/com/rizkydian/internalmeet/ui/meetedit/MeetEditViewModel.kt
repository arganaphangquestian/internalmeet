package com.rizkydian.internalmeet.ui.meetedit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
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

class MeetEditViewModel @ViewModelInject constructor(
    private val meetRepo: MeetingRepository,
    userRepo: UserRepository
) : ViewModel() {
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

    fun edit(id: String?): MutableLiveData<NetworkState> {
        if (
            !meet.value?.name.isNullOrEmpty() &&
            !meet.value?.place.isNullOrEmpty() &&
            !meet.value?.topic.isNullOrEmpty() &&
            !meet.value?.participant.isNullOrEmpty() &&
            !meetDate.value.isNullOrEmpty() &&
            !meetTime.value.isNullOrEmpty()
        ) {
            networkState.value = NetworkState.LOADING
            meetRepo.getByMeetID(id ?: "").get().addOnSuccessListener { documentSnapshots ->
                if (documentSnapshots.documents[0].toObject<Meet>() != null) {
                    meetRepo.getByID(documentSnapshots.documentChanges[0].document.id)
                        .update(
                            mapOf(
                                "name" to meet.value!!.name,
                                "datetime" to "${meetDate.value!!} ${meetTime.value!!}",
                                "place" to meet.value!!.place,
                                "topic" to meet.value!!.topic,
                                "note" to meet.value!!.note,
                                "participant" to meet.value!!.participant,
                                "result" to meet.value!!.result,
                                "done" to meet.value!!.done
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
                                                    networkState.value =
                                                        NetworkState.error(t.message)
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
                }
            }
        } else {
            networkState.value = NetworkState.error("Please fill require field")
        }
        return networkState
    }

    fun get(id: String?): MutableLiveData<NetworkState> {
        networkState.value = NetworkState.LOADING
        meetRepo.getByMeetID(id ?: "").get()
            .addOnSuccessListener {
                if (it.documents.isNullOrEmpty()) {
                    networkState.value = NetworkState.error("Meet Not Found")
                } else {
                    meet.value = it.documents[0].toObject(Meet::class.java)
                    networkState.value = NetworkState.LOADED
                }
            }
            .addOnFailureListener { e ->
                networkState.value = NetworkState.error(e.message)
                Timber.e(e)
            }
        return networkState
    }
}
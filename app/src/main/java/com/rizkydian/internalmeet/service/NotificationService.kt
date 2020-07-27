package com.rizkydian.internalmeet.service

import com.rizkydian.internalmeet.data.AttendentBody
import com.rizkydian.internalmeet.data.AttendentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("send")
    fun pushNotification(@Body attendentBody: AttendentBody
    ): Call<AttendentResponse>
}
package com.rizkydian.internalmeet.service

import com.rizkydian.internalmeet.data.Notif
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NotificationService {
    @FormUrlEncoded
    @POST("send")
    fun pushNotification(
        @Field("to") to: String,
        @Field("notification") notification: Notif,
        @Field("data") data: Notif
    ): Void
}
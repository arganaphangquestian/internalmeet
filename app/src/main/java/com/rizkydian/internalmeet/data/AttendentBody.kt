package com.rizkydian.internalmeet.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttendentBody(
    @Json(name = "data")
    var `data`: Notif = Notif(),
    @Json(name = "notification")
    var notification: Notif = Notif(),
    @Json(name = "to")
    var to: String = ""
)
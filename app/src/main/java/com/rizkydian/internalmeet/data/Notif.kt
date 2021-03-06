package com.rizkydian.internalmeet.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Notif(
    @Json(name = "title")
    val title: String =  "",
    @Json(name = "body")
    val body: String =  ""
)
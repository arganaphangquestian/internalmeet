package com.rizkydian.internalmeet.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttendentResponse(
    @Json(name = "canonical_ids")
    var canonicalIds: Int = 0,
    @Json(name = "failure")
    var failure: Int = 0,
    @Json(name = "multicast_id")
    var multicastId: Long = 0,
    @Json(name = "results")
    var results: List<Result> = listOf(),
    @Json(name = "success")
    var success: Int = 0
)
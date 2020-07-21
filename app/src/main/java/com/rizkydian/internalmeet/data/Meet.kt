package com.rizkydian.internalmeet.data

data class Meet(
    val id: String? = null,
    var name: String = "",
    var members: List<String>? = null,
    var result: String? = null,
    var datetime: String = "", // Convert to date
    var fileLink: String? = null,
    var imageLink: String? = null,
    var place: String = "",
    var topic: String = "",
    var note: String? = null,
    var participant: String = "",
    var done: Boolean = false
)
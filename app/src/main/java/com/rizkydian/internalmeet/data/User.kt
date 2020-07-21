package com.rizkydian.internalmeet.data

data class User(
    var nip: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var imageProfile: String? = "",
    var position: String = "",
    var role: String = "",
    var meetings: List<Meet>? = null
)
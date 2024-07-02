package com.nezuko.domain.model

data class UserModel(
    var UserId: String = "",
    var photoUrl: String = "",
    var username: String = "",

    // список из ID плейлистов
    var playlistsList: ArrayList<Long> = ArrayList<Long>(),
) {

}

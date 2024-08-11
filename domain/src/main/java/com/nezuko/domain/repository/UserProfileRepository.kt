package com.nezuko.domain.repository

import com.nezuko.domain.model.UserProfile


interface UserProfileRepository {
    suspend fun registerUserProfile(
        user: UserProfile,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    )

    suspend fun getUserProfileByUID(
        uid: String,
        onSuccess: (UserProfile) -> Unit,
        onFailure: () -> Unit,
    )

    suspend fun updateUser(
        uid: String,
        newUser: UserProfile,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    suspend fun setAvatarToUser(
        user: UserProfile,
        uri: String,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    )

    suspend fun getImageFromStorageByUrl(
        url: String,
    ): String

    suspend fun getImageFromStorageByName(
        imageName: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {},
    ): String
}
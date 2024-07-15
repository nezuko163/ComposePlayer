package com.nezuko.domain.repository

import com.nezuko.domain.model.UserProfile


interface UserProfileRepository {
    suspend fun registerUserProfile(
        user: UserProfile,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    )
}
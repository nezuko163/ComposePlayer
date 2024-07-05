package com.nezuko.domain.usecase


import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.repository.UserProfileRepository

class RegisterUserProfileUseCase(
    private val impl: UserProfileRepository
) {
    suspend fun execute(user: UserProfile) = impl.registerUserProfile(user)
}
package com.nezuko.domain.usecase

import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.repository.UserProfileRepository

class SetAvatarToUserUseCase(
    private val impl: UserProfileRepository
) {
    suspend operator fun invoke(
        user: UserProfile,
        uri: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {}
    ) = impl.setAvatarToUser(
        user = user,
        uri = uri,
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}
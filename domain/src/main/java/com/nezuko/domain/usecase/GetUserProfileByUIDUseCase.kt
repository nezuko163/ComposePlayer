package com.nezuko.domain.usecase

import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.repository.UserProfileRepository

class GetUserProfileByUIDUseCase(
    private val impl: UserProfileRepository
) {
    suspend operator fun invoke(
        uid: String,
        onSuccess: (UserProfile) -> Unit = {},
        onFailure: () -> Unit = {},
    ) = impl.getUserProfileByUID(
        uid = uid,
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}
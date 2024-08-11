package com.nezuko.domain.usecase

import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.repository.UserProfileRepository

class GetImageFromStorageByNameUseCase(
    private val impl: UserProfileRepository
) {
    suspend operator fun invoke(
        imageName: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {},
    ) = impl.getImageFromStorageByName(
        imageName = imageName,
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}
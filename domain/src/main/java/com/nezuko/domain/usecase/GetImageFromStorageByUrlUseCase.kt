package com.nezuko.domain.usecase

import com.nezuko.domain.repository.UserProfileRepository

class GetImageFromStorageByUrlUseCase(
    private val impl: UserProfileRepository
) {
    suspend operator fun invoke(
        url: String
    ) = impl.getImageFromStorageByUrl(url = url)
}
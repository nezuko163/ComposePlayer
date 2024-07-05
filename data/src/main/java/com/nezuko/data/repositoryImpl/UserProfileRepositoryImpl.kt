package com.nezuko.data.repositoryImpl

import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.repository.UserProfileRepository

class UserProfileRepositoryImpl: UserProfileRepository {
    override suspend fun registerUserProfile(user: UserProfile): Result<Boolean> {
        TODO("Not yet implemented")
    }
}
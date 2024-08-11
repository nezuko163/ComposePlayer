package com.nezuko.data.repositoryImpl

import android.util.Log
import androidx.core.net.toUri
import com.nezuko.data.source.remote.ApiRemote
import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.repository.UserProfileRepository

class UserProfileRepositoryImpl(
    private val apiRemote: ApiRemote
) : UserProfileRepository {

    private val TAG = "USER_PROFILE_IMPL"
    override suspend fun registerUserProfile(
        user: UserProfile,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        apiRemote.registerUserProfile(
            user = user,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override suspend fun getUserProfileByUID(
        uid: String,
        onSuccess: (UserProfile) -> Unit,
        onFailure: () -> Unit,
    ) =
        apiRemote.getUserProfileByUID(
            uid = uid,
            onSuccess = onSuccess,
            onFailure = onFailure
        )


    override suspend fun updateUser(
        uid: String,
        newUser: UserProfile,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        apiRemote.updateUserProfile(
            uid = uid,
            newUserProfile = newUser,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override suspend fun setAvatarToUser(
        user: UserProfile,
        uri: String,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        if (user.artUrl.isNotEmpty()) apiRemote.deleteImageFromFirebaseStorageByUrl(user.artUrl)

        val url = apiRemote.uploadImageToFirebaseStorage(
            imageUri = uri.toUri(),
            onSuccess = { str -> Log.i(TAG, "setAvatarToUser: uploading $str is successful") },
            onFailure = { Log.i(TAG, "setAvatarToUser: uploading is unsuccessful") }
        )

        user.artUrl = url.toString()

        apiRemote.updateUserProfile(
            uid = user.id,
            newUserProfile = user,
            onSuccess = { Log.i(TAG, "setAvatarToUser: set avatar $url " +
                    "\nto user with id = ${user.id} is successful") },
            onFailure = { Log.i(TAG, "setAvatarToUser: url = $url\n unluckk") }
        )
    }

    override suspend fun getImageFromStorageByUrl(url: String): String =
        url.also { Log.i(TAG, "getImageFromStorageByUrl: url = $url") }

    override suspend fun getImageFromStorageByName(
        imageName: String,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit,
    ): String = apiRemote.getUrlForFirebaseImageFromImageName(
        imageName = imageName,
        onSuccess = onSuccess,
        onFailure = onFailure
    ).toString().also { Log.i(TAG, "getImageFromStorageByName: url = $it") }
}


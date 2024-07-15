package com.nezuko.data.repositoryImpl

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.repository.UserProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserProfileRepositoryImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher,
    private val db: DatabaseReference = FirebaseDatabase.getInstance().reference
) : UserProfileRepository {
    override suspend fun registerUserProfile(
        user: UserProfile,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        withContext(ioDispatcher) {
            db.child("users").child(user.id).setValue(user)
                .addOnCompleteListener { onSuccess.invoke() }
                .addOnFailureListener { onFailure.invoke() }
        }
    }

}
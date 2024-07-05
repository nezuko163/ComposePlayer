package com.nezuko.data.repositoryImpl

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nezuko.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {
    private val TAG = "AUTH_LOGIN"
    override suspend fun registerViaEmailAndPassword(
        email: String,
        password: String,
        onRegisterComplete: (String) -> Unit
    ) = withContext(ioDispatcher) {

        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // послать запрос на добавление пользователей в users
                    // будет обработано в source/remote

                    onRegisterComplete.invoke(task.result.user!!.uid)
                } else {
                    //
                    Toast.makeText(context, "нипон", Toast.LENGTH_SHORT).show()
                    onRegisterComplete.invoke("")
                }
            }

        Result.failure<Boolean>(Exception())
    }

    override suspend fun registerViaGoogle(
        email: String,
        onRegisterComplete: (String) -> Unit
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun loginViaEmailAndPassword(
        email: String,
        password: String,
        onLoginComplete: (String) -> Unit
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun loginViaGoogle(
        email: String,
        onLoginComplete: (String) -> Unit
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUserID(): Result<String> = withContext(ioDispatcher) {
        try {
            Log.i(TAG, "getCurrentUserID: ")
            val auth = Firebase.auth
            val user = auth.currentUser

            if (user == null) {
                Result.failure(Exception("user not found"))
            } else {
                user.reload().await()
                if (user == null) {
                    Result.failure(Exception("user not found"))
                } else Result.success(user.uid)
            }
        } catch (_: Exception) {
            Result.failure(Exception("user not found"))
        }
    }
}

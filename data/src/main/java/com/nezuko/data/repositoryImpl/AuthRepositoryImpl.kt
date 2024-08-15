package com.nezuko.data.repositoryImpl

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
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
        onRegisterComplete: (String) -> Unit,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        withContext(ioDispatcher) {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        // послать запрос на добавление пользователей в users
                        // будет обработано в source/remote

                        onRegisterComplete.invoke(task.result.user!!.uid)
                        onSuccess.invoke()
                    } else {
                        //
                        Toast.makeText(context, "нипон", Toast.LENGTH_SHORT).show()
                        onRegisterComplete.invoke("")
                        onFailure.invoke()
                    }
                }
        }
    }

    override suspend fun registerViaGoogle(
        email: String,
        onRegisterComplete: (String) -> Unit,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun loginViaEmailAndPassword(
        email: String,
        password: String,
        onLoginComplete: (String) -> Unit,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        withContext(ioDispatcher) {

            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        // послать запрос на добавление пользователей в users
                        // будет обработано в source/remote

                        onLoginComplete.invoke(task.result.user!!.uid)
                        onSuccess.invoke()
                    } else {
                        //
                        Toast.makeText(context, "нипон", Toast.LENGTH_SHORT).show()
                        onLoginComplete.invoke("")
                        onFailure.invoke()
                    }
                }
        }
    }

    override suspend fun loginViaGoogle(
        email: String,
        onLoginComplete: (String) -> Unit,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun isEmailFree(
        email: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        withContext(ioDispatcher) {
            val auth = FirebaseAuth.getInstance()
            auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Получаем список методов входа, связанных с адресом электронной почты
                        val signInMethods = task.result?.signInMethods ?: emptyList()

                        if (signInMethods.isNotEmpty()) {
                            // Адрес электронной почты уже используется
                            Log.i(TAG, "isEmailFree: Email is already in use.")
                            onFailure()
                        } else {
                            // Адрес электронной почты доступен, регистрируем нового пользователя
                            onSuccess()
                        }
                    } else {
                        // Обработка ошибки при проверке email
                        val error = task.exception
                        when (error) {
                            is FirebaseAuthInvalidCredentialsException -> Log.i(
                                TAG,
                                "isEmailFree: Invalid email: ${error.message}"
                            )

                            else -> Log.i(
                                TAG,
                                "isEmailFree: Error checking email: ${error?.message}"
                            )
                        }
                        onFailure()
                    }
                }
        }
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

    override fun initializeApp() {
        FirebaseApp.initializeApp(context)
        Firebase.database.setPersistenceEnabled(true)
    }

    override fun enableOnlineMode() {
        Firebase.database.goOnline()
    }

    override fun disableOnlineMode() {
        Firebase.database.goOffline()
    }
}

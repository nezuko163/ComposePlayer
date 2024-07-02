package com.nezuko.data.source.remote

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nezuko.domain.model.UserModel

class ApiRemote {
    private val TAG = "API_REMOTE"
    val URL = "https://composeplayer-98512-default-rtdb.firebaseio.com/"


    // обработать одинаковые логины
    suspend fun registerProfile(
        id: String,
        username: String,
        photoUrl: String = "",
        onRegistrationComlete: (Boolean) -> Unit
    ) {
        val db = Firebase.database.reference

        val userRef = db.child("users")

        val user = UserModel(id, photoUrl, username)

        userRef.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i(TAG, "registerProfile: пользователь с именем = $username зареган")
                onRegistrationComlete(true)
            } else {
                Log.i(TAG, "registerProfile: пользователь с именем = $username не зареган")
                onRegistrationComlete(false)
            }
        }
    }
}
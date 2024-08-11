package com.nezuko.data.source.remote

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.nezuko.domain.model.Resource
import com.nezuko.domain.model.UserProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ApiRemote(
    private val ioDispatcher: CoroutineDispatcher,
) {
    private val TAG = "API_REMOTE"
    private val storage = FirebaseStorage.getInstance()
    private val db: DatabaseReference = FirebaseDatabase.getInstance().reference
    val URL = "https://composeplayer-98512-default-rtdb.firebaseio.com/"
    private val IMAGES = "images"

    private val URL_IMAGES = "gs://composeplayer-98512.appspot.com/images/"

    // storage
    private val imageRef = storage.reference.child(IMAGES)


    // database
    private val usersRef = db.child("users")


    suspend fun registerUserProfile(
        user: UserProfile,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        withContext(ioDispatcher) {
            Log.i(TAG, "registerUserProfile: $user")
            db.child("users").child(user.id).setValue(user)
                .addOnSuccessListener { onSuccess.invoke() }
                .addOnFailureListener { onFailure.invoke() }
        }
    }

    suspend fun updateUserProfile(
        uid: String,
        newUserProfile: UserProfile,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        withContext(ioDispatcher) {
            suspendCancellableCoroutine { continuation ->
                usersRef.child(uid).updateChildren(newUserProfile.toMap())
                    .addOnSuccessListener {
                        Log.i(TAG, "updateUserProfile: $newUserProfile")
                        continuation.resume(Unit)
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                        onFailure()
                    }
            }
        }
    }

    suspend fun getUserProfileByUID(
        uid: String,
        onSuccess: (UserProfile) -> Unit,
        onFailure: () -> Unit,
    ) {
        withContext(ioDispatcher) {
            val user = db.child("users").child(uid).get()
                .addOnSuccessListener { snapshot ->
                    val user = snapshot.getValue(UserProfile::class.java)
                        ?: return@addOnSuccessListener
                    Log.i(TAG, "getUserProfileByUID: $user")
                    onSuccess(user)
                }
                .addOnFailureListener { exception ->
                    Log.i(TAG, "getUserProfileByUID: $exception")
                    onFailure()
                }.await().getValue(UserProfile::class.java)
            if (user == null) {
                onFailure()
                return@withContext
            }
            if (user.artUrl.isNotEmpty()) user.artUrl =
                getUrlForFirebaseImageFromUrl(user.artUrl).toString()
            if (user.backgroundArtUrl.isNotEmpty()) user.backgroundArtUrl =
                getUrlForFirebaseImageFromUrl(user.backgroundArtUrl).toString()
//            db.child("users").child(uid)
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        val user = snapshot.getValue(UserProfile::class.java) ?: return
//                        Log.i(TAG, "onDataChange: $user")
//                        onSuccess(user)
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        onFailure()
//                        Log.e(TAG, "onCancelled: ${error.message}")
//                    }
//                })
            onSuccess(user)
        }
    }


    // что бля а нахуя мне эта функция если у меня и так есть юрл имаги
    // что блять!?!?
    suspend fun getUrlForFirebaseImageFromUrl(
        url: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {},
    ) =
        withContext(ioDispatcher) {
            storage.getReferenceFromUrl(url).downloadUrl
                .addOnSuccessListener { uri: Uri ->
                    onSuccess(uri.toString())
                }
                .addOnFailureListener { onFailure() }
                .await()
        }

    suspend fun getUrlForFirebaseImageFromImageName(
        imageName: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {}
    ) = withContext(ioDispatcher) {
        imageRef.child(imageName).downloadUrl
            .addOnSuccessListener { uri: Uri ->
                onSuccess(uri.toString())
            }
            .addOnFailureListener { onFailure() }
            .await()
    }

    suspend fun uploadImageToFirebaseStorage(
        imageUri: Uri,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {}
    ) = withContext(ioDispatcher) {
        val ref = imageRef.child(imageUri.lastPathSegment!!)
        val uploadTask = ref.putFile(imageUri)
        uploadTask
            .addOnSuccessListener { }
            .addOnFailureListener {
                onFailure()
                return@addOnFailureListener
            }.await()

        getUrlForFirebaseImageFromImageName(
            imageName = ref.name,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    suspend fun deleteImageFromFirebaseStorageByUrl(
        url: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {}
    ): Resource<String> = withContext(ioDispatcher) {
        suspendCancellableCoroutine { continuation ->
            val imageRef = storage.getReferenceFromUrl(url)

            imageRef.delete()
                .addOnSuccessListener {
                    onSuccess(url)
                    continuation.resume(Resource.Success(url))
                }
                .addOnFailureListener { exception ->
                    onFailure()
                    continuation.resume(Resource.Error(exception.message!!))
                }
        }
    }

    suspend fun deleteImageFromFirebaseStorageByImageName(
        imageName: String,
        onSuccess: (String) -> Unit = {},
        onFailure: () -> Unit = {}
    ): Resource<String> = deleteImageFromFirebaseStorageByUrl(
        url = getUrlForFirebaseImageFromImageName(imageName).toString(),
        onSuccess = onSuccess,
        onFailure = onFailure
    )



}

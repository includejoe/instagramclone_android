package org.includejoe.instagramclone.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.includejoe.instagramclone.domain.model.User
import org.includejoe.instagramclone.domain.repository.UserRepository
import org.includejoe.instagramclone.util.Constants
import org.includejoe.instagramclone.util.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): UserRepository {
    private var operationSuccessful = false

    override fun getUserDetails(
        userId: String
    ): Flow<Response<User>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore
            .collection(Constants.COLLECTION_USERS)
            .document(userId)
            .addSnapshotListener{ snapshot, error ->
                val response = if(snapshot != null) {
                    val userInfo = snapshot.toObject(User::class.java)
                    Response.Success<User>(userInfo!!)
                } else {
                    Response.Error<String>(error?.message?:error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun setUserDetails(
        userId: String,
        name: String,
        username: String,
        bio: String,
        url: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            val userObj = mutableMapOf<String, String>()
            userObj["name"] = name
            userObj["username"] = username
            userObj["bio"] = bio
            userObj["url"] = url
            firebaseFirestore
                .collection(Constants.COLLECTION_USERS)
                .document(userId)
                .update(userObj as Map<String, Any>)
                .addOnSuccessListener {
                    operationSuccessful = true
                }.await()

            if(operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error<String>("Could not update profile"))
            }

        } catch (e: Exception) {
            Response.Error<String>(e.localizedMessage?:"An unexpected error occurred")
        }
    }
}
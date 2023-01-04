package org.includejoe.instagramclone.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.includejoe.instagramclone.domain.model.User
import org.includejoe.instagramclone.domain.repository.AuthenticationRepository
import org.includejoe.instagramclone.util.Constants
import org.includejoe.instagramclone.util.Response
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthenticationRepository{
    var operationSuccessful: Boolean = false

    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(
        email: String,
        password: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            auth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                operationSuccessful = true
            }.await()
            emit(Response.Success(operationSuccessful))
        } catch(e: Exception) {
            emit(Response.Error<String>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error<String>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        username: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            auth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                operationSuccessful = true
            }
            if(operationSuccessful) {
                val userId = auth.currentUser?.uid!!
                val obj = User(
                    username = username,
                    userId = userId,
                    password = password,
                    email = email
                )
                firestore
                    .collection(Constants.COLLECTION_USERS)
                    .document(userId)
                    .set(obj)
                    .addOnSuccessListener {}.await()
                emit(Response.Success(operationSuccessful))
            } else {
                Response.Success(operationSuccessful)
            }
        } catch(e: Exception) {
            emit(Response.Error<String>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }

}
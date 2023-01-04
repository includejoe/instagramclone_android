package org.includejoe.instagramclone.domain.repository

import kotlinx.coroutines.flow.Flow
import org.includejoe.instagramclone.util.Response

interface AuthenticationRepository {

    fun isUserAuthenticatedInFirebase() : Boolean

    fun getFirebaseAuthState(): Flow<Boolean>

    fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>>

    fun firebaseSignOut(): Flow<Response<Boolean>>

    fun firebaseSignUp(email: String, password: String, username: String): Flow<Response<Boolean>>
}
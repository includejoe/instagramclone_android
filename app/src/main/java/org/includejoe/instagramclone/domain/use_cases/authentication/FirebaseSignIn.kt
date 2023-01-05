package org.includejoe.instagramclone.domain.use_cases.authentication

import org.includejoe.instagramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ) = repository.firebaseSignIn(email, password)
}
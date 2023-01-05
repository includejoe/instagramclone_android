package org.includejoe.instagramclone.domain.use_cases.authentication

import org.includejoe.instagramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignOut @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke() = repository.firebaseSignOut()
}
package org.includejoe.instagramclone.domain.use_cases.user


import org.includejoe.instagramclone.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(
        userId: String
    ) = repository.getUserDetails(userId = userId)
}
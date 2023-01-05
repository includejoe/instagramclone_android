package org.includejoe.instagramclone.domain.use_cases.user

import org.includejoe.instagramclone.domain.repository.UserRepository
import javax.inject.Inject

class SetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(
        userId: String,
        name: String,
        username: String,
        bio: String,
        url: String
    ) = repository.setUserDetails(
        userId = userId,
        name = name,
        username = username,
        bio = bio,
        url = url
    )
}
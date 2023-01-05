package org.includejoe.instagramclone.domain.repository

import kotlinx.coroutines.flow.Flow
import org.includejoe.instagramclone.domain.model.User
import org.includejoe.instagramclone.util.Response

interface UserRepository {
    fun getUserDetails(userId: String): Flow<Response<User>>

    fun setUserDetails(
        userId: String,
        name: String,
        username: String,
        bio: String,
        url: String,
    ): Flow<Response<Boolean>>
}
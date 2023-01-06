package org.includejoe.instagramclone.domain.repository

import kotlinx.coroutines.flow.Flow
import org.includejoe.instagramclone.domain.model.Post
import org.includejoe.instagramclone.util.Response

interface PostRepository {
    fun getAllPosts(userId: String): Flow<Response<List<Post>>>

    fun uploadPost(
        postImage: String,
        postDescription: String,
        time: Long,
        userId: String,
        username: String,
        userImage: String,
    ): Flow<Response<Boolean>>
}
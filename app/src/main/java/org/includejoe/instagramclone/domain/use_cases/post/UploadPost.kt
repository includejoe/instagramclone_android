package org.includejoe.instagramclone.domain.use_cases.post

import org.includejoe.instagramclone.domain.repository.PostRepository
import javax.inject.Inject

class UploadPost @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(
        userId: String,
        username: String,
        userImage: String,
        postImage: String,
        postDescription: String,
        time: Long,
    ) = repository.uploadPost(
        userId = userId,
        username = username,
        userImage = userImage,
        postImage = postImage,
        postDescription = postDescription,
        time = time,
    )
}
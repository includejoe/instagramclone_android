package org.includejoe.instagramclone.domain.use_cases.post

import org.includejoe.instagramclone.domain.repository.PostRepository
import javax.inject.Inject

class GetAllPosts @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(userId: String) = repository.getAllPosts(userId=userId)
}
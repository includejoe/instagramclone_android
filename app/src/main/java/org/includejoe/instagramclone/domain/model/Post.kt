package org.includejoe.instagramclone.domain.model

data class Post(
    var postId: String = "",
    var postImage: String = "",
    var postDescription: String = "",
    var time: Long? = null,
    var userId: String = "",
    var userImage: String = "",
    var username: String = ""
)

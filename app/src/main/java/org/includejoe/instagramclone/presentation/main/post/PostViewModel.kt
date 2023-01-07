package org.includejoe.instagramclone.presentation.main.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.includejoe.instagramclone.domain.model.Post
import org.includejoe.instagramclone.domain.use_cases.post.PostUseCases
import org.includejoe.instagramclone.util.Response
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
): ViewModel() {
    private val _postData = mutableStateOf<Response<List<Post>>>(Response.Loading)
    val postData : State<Response<List<Post>>> = _postData

    private val _uploadPostData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val uploadPostData : State<Response<Boolean>> = _uploadPostData

    fun getAllPosts(userId: String) {
        viewModelScope.launch {
            postUseCases.getAllPosts(userId).collect{
                _postData.value = it
            }
        }
    }

    fun uploadPost(
        postImage: String,
        postDescription: String,
        time: Long,
        userId: String,
        username: String,
        userImage: String,
    ) {
        viewModelScope.launch {
            postUseCases.uploadPost(
                postImage = postImage,
                postDescription = postDescription,
                time = time,
                userId = userId,
                username = username,
                userImage = userImage,
            ).collect {
                _uploadPostData.value = it
            }
        }
    }
}
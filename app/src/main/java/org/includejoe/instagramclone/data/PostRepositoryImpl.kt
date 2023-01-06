package org.includejoe.instagramclone.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.includejoe.instagramclone.domain.model.Post
import org.includejoe.instagramclone.domain.repository.PostRepository
import org.includejoe.instagramclone.util.Constants
import org.includejoe.instagramclone.util.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): PostRepository {
    private var operationSuccessful = false

    override fun getAllPosts(
        userId: String
    ): Flow<Response<List<Post>>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore
            .collection(Constants.COLLECTION_POSTS)
            .whereNotEqualTo("userId", userId)
            .addSnapshotListener{ snapshot, error ->
                val response = if(snapshot!=null) {
                    val postsList = snapshot.toObjects(Post::class.java)
                    Response.Success<List<Post>>(postsList)
                } else {
                    Response.Error<String>(error?.message?:error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun uploadPost(
        postImage: String,
        postDescription: String,
        time: Long,
        userId: String,
        username: String,
        userImage: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            val postId = firebaseFirestore
                .collection(Constants.COLLECTION_POSTS)
                .document()
                .id
            val post = Post(
                postId = postId,
                postImage = postImage,
                postDescription = postDescription,
                time = time,
                username = username,
                userImage = userImage,
                userId = userId
            )
            firebaseFirestore
                .collection(Constants.COLLECTION_POSTS)
                .document(postId)
                .set(post)
                .addOnSuccessListener {
                    operationSuccessful = true
                }.await()
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error<String>("Post could not be created"))
            }
        } catch(e: Exception) {
            emit(Response.Error<String>(e.localizedMessage?:"An Unexpected error occurred"))
        }
    }
}
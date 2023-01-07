package org.includejoe.instagramclone.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import org.includejoe.instagramclone.domain.model.Post

@Composable
fun PostCard(post: Post) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = rememberImagePainter(data = post.userImage),
                contentDescription = "ProfileImage",
                tint = Color.Unspecified
            )
            Text(
                text = post.username,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                textAlign = TextAlign.Left
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "more"
            )
        }
        Icon(
            painter = rememberImagePainter(data = post.postImage),
            contentDescription = "post image",
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        )
        Text(
            text = post.postDescription,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(30.dp)
        )
    }
}

@Composable
fun FeedsContent(postsList: List<Post>) {
    LazyColumn {
        items(postsList) {
            PostCard(post = it)
        }
    }
}
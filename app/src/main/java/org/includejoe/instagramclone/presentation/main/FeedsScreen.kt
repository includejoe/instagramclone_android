package org.includejoe.instagramclone.presentation.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.includejoe.instagramclone.presentation.components.BottomNavigationItem
import org.includejoe.instagramclone.presentation.components.BottomNavigationMenu
import org.includejoe.instagramclone.presentation.components.Toast
import org.includejoe.instagramclone.presentation.main.post.PostViewModel
import org.includejoe.instagramclone.util.Response
import org.includejoe.instagramclone.R

@Composable
fun FeedsScreen(
    navController: NavHostController
) {
    val postViewModel: PostViewModel = hiltViewModel()
//    postViewModel.getAllPosts()

    when(val response = postViewModel.postData.value) {
        is Response.Loading -> {

        }

        is Response.Success -> {
            val obj = response.data
            Scaffold(
                topBar = {
                TopAppBar(
                    title = { Text(text = "Instagram") },
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 10.dp,
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = "logo"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_message),
                                contentDescription = "message",
                                modifier = Modifier
                                    .size(30.dp)
                                    .rotate(-45f),
                                tint = Color.Unspecified
                            )
                        }
                    }
                )},
                content = {
//                    FeedsContent()
                },
                bottomBar = {
                    BottomNavigationMenu(
                        selectedItem = BottomNavigationItem.FEED,
                        navController = navController
                    )
                }
            )
        }

        is Response.Error<*> -> {
            Toast(message = response.message)
        }
    }
}
package org.includejoe.instagramclone.presentation.main.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import org.includejoe.instagramclone.R
import org.includejoe.instagramclone.domain.model.TabModel
import org.includejoe.instagramclone.presentation.components.BottomNavigationItem
import org.includejoe.instagramclone.presentation.components.BottomNavigationMenu
import org.includejoe.instagramclone.presentation.components.Toast
import org.includejoe.instagramclone.presentation.main.UserViewModel
import org.includejoe.instagramclone.presentation.main.profile.components.*
import org.includejoe.instagramclone.util.Response

@Composable
fun ProfileScreen(navController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    userViewModel.getUserInfo()
    when(val response = userViewModel.getUserDetails.value) {
        is Response.Loading -> {
            CircularProgressIndicator()
        }
        is Response.Success -> {
            if(response.data!=null) {
                val obj = response.data
                var selectedTabIndex by remember { mutableStateOf(0)}
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.weight(1f)) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = obj.username,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 22.sp,
                                )
                            },
                            actions = {
                                Icon(
                                    painter = painterResource(id =  R.drawable.ic_new),
                                    contentDescription = "Create",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon (
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = "Menu",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            backgroundColor = Color.White,
                            elevation = 10.dp
                        )
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 10.dp,
                                        start = 10.dp,
                                        bottom = 10.dp,
                                        end = 20.dp
                                    )
                            ) {
                                RoundedImage(
//                                    image = rememberImagePainter(data=obj.imageUrl),
                                    image = painterResource(id = R.drawable.banner5),
                                    modifier = Modifier
                                        .size(80.dp)
                                        .weight(3.5f)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(6.5f)
                                ) {
                                    ProfileStats(
                                        numberText="123",
                                        text="Posts",
                                        navController = navController
                                    )
                                    ProfileStats(
//                                        numberText=obj.followers.size.toString(),
                                        numberText="6.6M",
                                        text="Followers",
                                        navController = navController
                                    )
                                    ProfileStats(
                                        numberText=obj.following.size.toString(),
                                        text="Following",
                                        navController = navController
                                    )
                                }
                            }
                        }
                        MyProfile(
                            displayName=obj.name,
                            bio=obj.bio,
                            url=obj.url
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            ActionButton(
                                text="Edit Profile",
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .weight(0.45f)
                                    .height(35.dp)
                                    .clickable {

                                    }
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        TabView(
                            tabModels = listOf(
                                TabModel(
                                    image = painterResource(id = R.drawable.ic_grid),
                                    text = "Posts"
                                ),
                                TabModel(
                                        image = painterResource(id = R.drawable.ic_reels),
                                        text = "Posts"
                                ),
                                TabModel(
                                    image = painterResource(id = R.drawable.ic_igtv),
                                    text = "Posts"
                                )
                            )
                        ) {
                            selectedTabIndex = it
                        }
                        when(selectedTabIndex) {
                            0 -> {
                                PostsSection(
                                    posts = listOf(
                                        painterResource(id = R.drawable.banner1),
                                        painterResource(id = R.drawable.banner2),
                                        painterResource(id = R.drawable.banner3),
                                        painterResource(id = R.drawable.banner4),
                                        painterResource(id = R.drawable.banner5),
                                        painterResource(id = R.drawable.banner6),
                                        painterResource(id = R.drawable.banner7),
                                        painterResource(id = R.drawable.banner8),
                                        painterResource(id = R.drawable.banner9)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                )
                            }

                            1 -> {

                            }

                            2 -> {

                            }
                        }
                    }
                    BottomNavigationMenu(
                        selectedItem = BottomNavigationItem.PROFILE,
                        navController = navController
                    )

                }
            }
        }
        is Response.Error<*> -> {
            Toast(message = response.message)
        }
    }
}
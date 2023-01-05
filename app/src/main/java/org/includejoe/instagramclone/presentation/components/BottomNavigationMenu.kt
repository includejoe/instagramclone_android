package org.includejoe.instagramclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.includejoe.instagramclone.util.Screens

enum class BottomNavigationItem(
    val icon: ImageVector,
    val route: String
) {
    FEED(Icons.Default.Home, Screens.FeedsScreen.route),
    SEARCH(Icons.Default.Search, Screens.SearchScreen.route),
    PROFILE(Icons.Default.Person, Screens.ProfileScreen.route)
}

@Composable
fun BottomNavigationMenu(
    selectedItem: BottomNavigationItem,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for(item in BottomNavigationItem.values()) {
            Image(
                imageVector = item.icon,
                contentDescription = "menu icon",
                modifier = Modifier
                    .size(45.dp)
                    .padding(5.dp)
                    .clickable {
                        navController.navigate(item.route)
                    },
                colorFilter = if(item==selectedItem) ColorFilter.tint(Color.Black)
                              else ColorFilter.tint(Color.Gray)
            )
        }
    }
}
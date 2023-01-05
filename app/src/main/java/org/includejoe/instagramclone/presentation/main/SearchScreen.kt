package org.includejoe.instagramclone.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.includejoe.instagramclone.presentation.components.BottomNavigationItem
import org.includejoe.instagramclone.presentation.components.BottomNavigationMenu


@Composable
fun SearchScreen(
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "SEARCH SCREEN")
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.SEARCH,
            navController = navController
        )
    }
}
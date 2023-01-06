package org.includejoe.instagramclone.presentation.main.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.includejoe.instagramclone.domain.model.TabModel

@Composable
fun TabView(
    modifier: Modifier = Modifier.fillMaxWidth(),
    tabModels: List<TabModel>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0)}
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier
    ) {
        tabModels.forEachIndexed{ index, item ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color(0xFF777777)
            ) {
                Image(
                    painter = item.image,
                    contentDescription = item.text,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .size(37.dp)
                )
            }
        }
    }   
}
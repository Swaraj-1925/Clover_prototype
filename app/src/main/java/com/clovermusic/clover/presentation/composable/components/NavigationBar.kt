package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clovermusic.clover.R
import com.clovermusic.clover.presentation.navigation.HomeScreenRoute

@Composable
fun NavigationBar(
    navController: NavController
) {
    val home = painterResource(id = R.drawable.home)
    val search = painterResource(id = R.drawable.search)
    val library = painterResource(id = R.drawable.library)
    val profile = painterResource(id = R.drawable.profile)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(14.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp)


            ) {
                NavigationItem(
                    icon = home,
                    contentDescription =
                    "Home",
                    onClick = { navController.navigate(HomeScreenRoute) }
                )
                NavigationItem(
                    icon = search,
                    contentDescription = "Search",
                    onClick = {}
                )
                NavigationItem(
                    icon = library,
                    contentDescription = "Library",
                    onClick = {}
                )
                NavigationItem(
                    icon = profile,
                    contentDescription = "Profile",
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun NavigationItem(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(24.dp)
        )
    }
}
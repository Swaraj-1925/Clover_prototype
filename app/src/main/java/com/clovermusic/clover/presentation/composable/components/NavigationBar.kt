package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.R

@Composable
fun NavigationBar(
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLibraryClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val home = painterResource(id = R.drawable.home)
    val search = painterResource(id = R.drawable.search)
    val library = painterResource(id = R.drawable.library)
    val profile = painterResource(id = R.drawable.profile)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .clip(RoundedCornerShape(14.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .size(55.dp)
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
            ) {
                IconButton(
                    onClick = { onHomeClick() },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = home,
                        contentDescription = "Home screen icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = { onSearchClick() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = search,
                        contentDescription = "Home screen icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = { onLibraryClick() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = library,
                        contentDescription = "Home screen icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = { onProfileClick() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = profile,
                        contentDescription = "Home screen icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
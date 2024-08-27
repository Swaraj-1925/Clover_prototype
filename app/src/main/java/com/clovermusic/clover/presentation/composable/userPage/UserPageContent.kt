package com.clovermusic.clover.presentation.composable.userPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clovermusic.clover.R

@Composable
fun UserPageBody() {
    Box(modifier = Modifier
        .fillMaxHeight(1f)) {
        // Surface with user details
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .height(600.dp)
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp, end = 28.dp, top = 30.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Spacer(modifier = Modifier.height(8.dp))

                        // User Information
                        Text(
                            text = "Neel Lito",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Photographer",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Text(
                            text = "Indore, India",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    FilledTonalButton(
                        onClick = { },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults
                            .buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .height(56.dp)
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "+ Add",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                // User Stats
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    UserStat(title = "624", subtitle = "Following")
                    UserStat(title = "142", subtitle = "Followers")
                    UserStat(title = "104", subtitle = "Playlists")
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp)) {
                    Text(text = "Playlists",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.surface)
                Spacer(modifier = Modifier.height(8.dp))

//                    UserPagePlaylistSection()
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.46f)
                .align(Alignment.Center)
                .padding(start = 45.dp)
        ) {
            // User Image
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .align(Alignment.TopStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg3),
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
            }
        }
    }
}

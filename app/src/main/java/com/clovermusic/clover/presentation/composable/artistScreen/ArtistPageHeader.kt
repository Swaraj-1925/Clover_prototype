package com.clovermusic.clover.presentation.composable.artistScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.presentation.composable.components.getThemedIcons

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistPageHeader(
    artist: ArtistsEntity,
    navController: NavController
) {
    val icons = getThemedIcons()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.End)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF2C2C2C)
                        ),
                        startX = Float.POSITIVE_INFINITY,
                        endX = 0f
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(top = 36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back3),
                        contentDescription = "back",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .size(36.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                ) {
                    AsyncImage(
                        model = artist.imageUrl,
                        contentDescription = artist.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterVertically)

                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Top)
                            .padding(horizontal = 20.dp, vertical = 32.dp)
                    ) {
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.headlineLarge
                                .copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 36.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                        Text(
                            text = "${artist.followers} monthly listeners",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            Column {
                                Text(
                                    text = "10.2M",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Followers",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontSize = 8.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )

                            }
                            Column {
                                Text(
                                    text = "21",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Albums",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontSize = 8.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                            Column {
                                Text(
                                    text = "1075",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Songs",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontSize = 8.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilledTonalButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(180.dp, 50.dp)
                            .align(Alignment.CenterVertically),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            text = "FOLLOW",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    FilledTonalButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(180.dp, 50.dp)
                            .align(Alignment.CenterVertically),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                        ) {
                        Text(
                            text = "FOLLOW",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }


    }

}



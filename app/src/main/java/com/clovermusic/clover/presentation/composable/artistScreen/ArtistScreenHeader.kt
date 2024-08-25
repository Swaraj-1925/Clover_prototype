package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.util.Parsers

@Composable
fun ArtistScreenHeader(
    artist: ArtistsEntity,
    numAlbums: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
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
                    .wrapContentHeight()
            ) {
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
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            Column {
                                Text(
                                    text = Parsers.formatNumber(artist.followers ?: 0),
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
                                    text = "$numAlbums",
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
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(180.dp, 50.dp)
                            .align(Alignment.CenterVertically),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = "Follow",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    FilledTonalButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(180.dp, 50.dp)
                            .align(Alignment.CenterVertically),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        Text(
                            text = "Play",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
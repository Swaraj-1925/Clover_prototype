package com.clovermusic.clover.presentation.composable.artistScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.presentation.composable.components.getThemedIcons

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistPageHeader(
    artist: ArtistsEntity,
    modifier: Modifier =Modifier
){
    val icons = getThemedIcons()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
        ) {
            Card(onClick = { /*TODO*/ }) {
                AsyncImage(
                    model = artist.imageUrl,
                    contentDescription = artist.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd)
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
            )
            Text(
                text =artist.name,
                style = MaterialTheme.typography.headlineLarge
                    .copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 50.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back3),
                    contentDescription = "back",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(36.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(start = 20.dp)
        ) {
            Text(text = "${artist.followers} monthly listeners",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.Bottom))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 20.dp)
        ) {
            OutlinedButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(86.dp, 35.dp)
                    .align(Alignment.CenterVertically)) {
                Text(text = "FOLLOW",
                    style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterVertically)) {
                Image(painter = painterResource(id = R.drawable.more_vertical),
                    contentDescription = "More")
            }
            Spacer(modifier = Modifier.width(130.dp))
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterVertically)) {
                Image(painter = painterResource(id = icons.shuffleButtonInactive),
                    contentDescription = "Shuffle",
                    modifier = Modifier
                        .size(30.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterVertically)) {
                Image(painter = painterResource(id = R.drawable.play_circle),
                    contentDescription = "Play",
                    modifier = Modifier
                        .size(44.dp))
            }
        }
    }
}



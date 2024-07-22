package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.ui.theme.CloverTheme

@Composable
fun SongListCard() {
    val moreButton = painterResource(id = R.drawable.more_vertical)
    val songImage = painterResource(id = R.drawable.ablum1)
    val songName = "Song Name"
    val songArtist = "Song Artist"
    val songDuration = 24.2f
    Column (modifier = Modifier.fillMaxSize()){
        ElevatedCard(onClick = { /*TODO*/ }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "1",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(8.dp)
                )
                Card(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Image(
                        painter = songImage,
                        contentDescription = songName,
                    )
                }
                Column (
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(horizontal = 8.dp)
                ){
                    Text(
                        text = songName,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = songArtist,
                            style = MaterialTheme.typography.labelMedium,
                        )
                        Text(
                            text = " • ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "$songDuration",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(0.1f)
                ) {

                    Icon(painter = moreButton, contentDescription = "More")
                }
            }
        }
    }
}
@Preview
@Composable
fun SongListCardPreview() {
    CloverTheme {
        SongListCard()
    }
}
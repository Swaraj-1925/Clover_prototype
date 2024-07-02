package com.clovermusic.clover.presentation.composable.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.R
import com.clovermusic.clover.ui.theme.CloverTheme

@Composable
fun NewReleaseSection() {
    val images = listOf(
        R.drawable.ablum1,
        R.drawable.clover_spotify_png,
        R.drawable.playtriangle,
        R.drawable.settings,
        R.drawable.more_vertical,
    )
    val settingIcon = painterResource(id = R.drawable.settings)
//    Todo : make it Album or Track
    val new ="New Album by"
    val name = "Name album"

    Surface (
//        TODO : Change Background Color
        color = Color.Black,
        modifier = Modifier

    ) {
        Column (
            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.End
        ){
            Row (
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = new,
                        color = Color.White
                    )
                    Text(
                        text = name,
                        color = Color.White
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    Icon(
                        painter = settingIcon,
                        contentDescription = "Settings icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                    )

                }

            }

            NewReleaseCard(images = images)
        }
    }
}

@Preview
@Composable
fun NewReleasePreview() {
    CloverTheme(darkTheme = false) {
        NewReleaseSection()

    }
}


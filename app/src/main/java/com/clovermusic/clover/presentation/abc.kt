package com.clovermusic.clover.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import getDominantColorFromImageUrl
import kotlinx.coroutines.launch

@Composable
fun ImageWithDominantColorGradient(imageUrl: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var dominantColor by remember { mutableStateOf(Color.Transparent) }

    LaunchedEffect(imageUrl) {
        coroutineScope.launch {
            dominantColor = getDominantColorFromImageUrl(context, imageUrl)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        dominantColor,
                        dominantColor.copy(alpha = 1f),
                        Color.Transparent
                    )
                )
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(0.4f)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        // Add other UI elements here, like text or icons
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ImageWithDominantColorGradient(imageUrl = "https://i.scdn.co/image/ab6761610000e5ebe17c0aa1714a03d62b5ce4e0")
        }
    }
}

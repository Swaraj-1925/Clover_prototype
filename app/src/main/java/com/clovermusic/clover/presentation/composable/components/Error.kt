package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackbar(
    message: String,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar(
            message = message
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        SnackbarHost(hostState = snackbarHostState)
    }
}
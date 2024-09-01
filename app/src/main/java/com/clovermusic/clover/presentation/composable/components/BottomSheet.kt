package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MoreBottomSheet(
    options: List<BottomSheetOption>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.Start)
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = option.iconResId),
                    contentDescription = option.label,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(30.dp)
                )
                TextButton(onClick = option.onClick) {
                    Text(
                        text = option.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

data class BottomSheetOption(
    val iconResId: Int,
    val label: String,
    val onClick: () -> Unit
)

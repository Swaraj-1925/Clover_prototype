package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity

@Composable
fun MoreBottomSheet(
    track: TrackEntity,
    artists: List<ArtistsEntity>,
    options: List<BottomSheetOption>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        BottomSheetCard(
            track = track,
            artists = artists,
            modifier = Modifier.fillMaxWidth()
        )
//
//        Divider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.Start)
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = option.iconResId),
                    contentDescription = option.label,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(40.dp)
                )
                TextButton(onClick = option.onClick,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)) {
                    Text(
                        text = option.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
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

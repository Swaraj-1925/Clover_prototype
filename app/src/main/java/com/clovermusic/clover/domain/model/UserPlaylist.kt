package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.common.Image
import com.clovermusic.clover.domain.model.common.Owner

data class UserPlaylist(
    val collaborative: Boolean,
    val description: String,
    val id: String,
    val image: List<Image>,
    val name: String,
    val snapshotId: String,
    val owner: Owner,
    val uri: String,
    val tracks: Int
)
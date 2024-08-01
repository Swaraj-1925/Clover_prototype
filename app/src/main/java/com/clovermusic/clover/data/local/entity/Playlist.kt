package com.clovermusic.clover.data.local.entity

import com.clovermusic.clover.domain.model.common.Owner

data class PlaylistEntity(
    val collaborative: Boolean,
    val description: String,
    val followers: Int?,
    val id: String,
    val name: String,
    val snapshotId: String,
    val owner: Owner,
    val uri: String,
)

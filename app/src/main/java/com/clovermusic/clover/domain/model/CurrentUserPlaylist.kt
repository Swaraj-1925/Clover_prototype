package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.util.Image


data class CurrentUserPlaylist(
    val collaborative: Boolean,
    val description: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val primary_color: String?,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: Int,
    val type: String,
    val uri: String
)

data class Owner(
    val display_name: String,
    val id: String,
    val type: String,
    val uri: String
)



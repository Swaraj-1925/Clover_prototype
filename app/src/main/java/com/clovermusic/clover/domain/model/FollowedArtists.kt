package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.util.Image


data class FollowedArtists(
    val followers: Int,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)



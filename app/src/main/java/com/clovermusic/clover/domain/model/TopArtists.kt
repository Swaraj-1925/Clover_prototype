package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.util.Image

data class TopArtists(
    val followers: Int,
    val genres: List<String>,
    val id: String,
    val image: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String,
    val total: Int
)

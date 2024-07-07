package com.clovermusic.clover.data.api.spotify.response.util


data class AddedByResponse(
    val followers: FollowersResponse,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
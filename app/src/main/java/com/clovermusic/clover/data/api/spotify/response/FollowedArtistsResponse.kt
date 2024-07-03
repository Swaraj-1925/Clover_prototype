package com.clovermusic.clover.data.api.spotify.response


data class FollowedArtistsResponse(
    val artists: Artists
)

data class Artists(
    val cursors: Cursors,
    val href: String,
    val items: List<ArtistResponseItem>,
    val limit: Int,
    val next: String,
    val total: Int
)

data class Cursors(
    val after: String
)





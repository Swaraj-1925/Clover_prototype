package com.clovermusic.clover.data.providers

import com.clovermusic.clover.data.providers.artist.Albums
import com.clovermusic.clover.data.providers.playlist.CurrentUserPlaylistInfo
import com.clovermusic.clover.data.providers.playlist.GetPlaylist
import com.clovermusic.clover.data.providers.user.FollowedArtists
import com.clovermusic.clover.data.providers.user.TopArtists
import javax.inject.Inject

data class Providers @Inject constructor(
    val albums: Albums,
    val currentUserPlaylistInfo: CurrentUserPlaylistInfo,
    val followedArtists: FollowedArtists,
    val topArtists: TopArtists,
    val getPlaylist: GetPlaylist
)

package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.playlists.PlaylistResponseDto
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.mapper.Util.toOwner
import com.clovermusic.clover.domain.model.Playlist
//Object of PlaylistResponseDto are mapped here and Playlist is created
fun PlaylistResponseDto.toPlaylist(): Playlist {
    return Playlist(
        collaborative = collaborative,
        description = description,
        followers = followers.total,
        id = id,
        image = images.toImages(),
        name = name,
        snapshotId = snapshot_id,
        owner = owner.toOwner(),
        uri = uri,
        tracks = tracks.items.toPlaylistItems()
    )

}
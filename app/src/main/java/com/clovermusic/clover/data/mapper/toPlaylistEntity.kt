package com.clovermusic.clover.data.mapper

import com.clovermusic.clover.data.local.entity.ImageType
import com.clovermusic.clover.data.local.entity.PlaylistEntity
import com.clovermusic.clover.data.spotify.api.dto.playlists.PlaylistResponseDto
import com.clovermusic.clover.domain.mapper.Util.toOwner

fun PlaylistResponseDto.toPlaylistEntity(imageOwnerId: String, type: ImageType): PlaylistEntity {
    return PlaylistEntity(
        collaborative = collaborative,
        description = description,
        followers = followers.total,
        id = id,
        name = name,
        snapshotId = snapshot_id,
        owner = owner.toOwner(),
        uri = uri,
    )

}

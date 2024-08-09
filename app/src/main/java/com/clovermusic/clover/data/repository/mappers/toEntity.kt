package com.clovermusic.clover.data.repository.mappers

import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.CollaboratorsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.spotify.api.dto.common.AddedByResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.PlaylistTrackResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackItemsResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.PlaylistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.UsersPlaylistItemDto
import com.clovermusic.clover.data.spotify.api.dto.users.UsersProfileResponseDto
import java.time.LocalDate

@JvmName("PlaylistInfoEntity")
fun List<UsersPlaylistItemDto>.toEntity(): List<PlaylistInfoEntity> {
    return map {
        PlaylistInfoEntity(
            playlistId = it.id,
            uri = it.uri,
            collaborative = it.collaborative,
            description = it.description,
            name = it.name,
            owner = it.owner.display_name,
            ownerId = it.owner.id,
            snapshotId = it.snapshot_id,
            totalTrack = it.tracks.total,
            imageUrl = it.images.firstOrNull()?.url ?: "",
            timestamp = System.currentTimeMillis(),
        )
    }
}


fun List<PlaylistTrackResponseDto>.toEntity(): List<TrackEntity> {
    return map { tracks ->
        TrackEntity(
            trackId = tracks.track.id,
            albumId = tracks.track.album.id,
            uri = tracks.track.uri,
            durationMs = tracks.track.duration_ms,
            name = tracks.track.name,
            imageUrl = tracks.track.album.images
                .filterNotNull() // Filter out null images
                .sortedBy { it.height * it.width } // Sort images by size (smallest first)
                .let { sortedImages ->
                    if (sortedImages.size > 1) {
                        sortedImages[1].url // Get the 2nd smallest image
                    } else {
                        sortedImages.firstOrNull()?.url ?: "" // If only one image, use it
                    }
                },
            previewUrl = tracks.track.preview_url,
            timestamp = System.currentTimeMillis(),
        )
    }
}

fun AddedByResponseDto.toEntity(name: String = "", imageUrl: String = ""): CollaboratorsEntity {
    return CollaboratorsEntity(
        collaboratorId = id,
        name = name,
        imageUrl = imageUrl,
        timeStamps = System.currentTimeMillis()
    )
}

fun List<TrackArtistResponseDto>.toEntity(
    followed: Boolean = false,
    top: Boolean = false
): List<ArtistsEntity> {
    return map {
        ArtistsEntity(
            artistId = it.id,
            uri = it.uri,
            genres = it.genres ?: emptyList(),
            imageUrl = it.images?.firstOrNull()?.url ?: "",
            name = it.name,
            isFollowed = followed,
            isTopArtist = top,
            timestamp = System.currentTimeMillis(),
        )
    }
}

fun AlbumResponseDto.toEntity(artistId: String): AlbumEntity {
    return AlbumEntity(
        albumId = id,
        uri = uri,
        name = name,
        imageUrl = images.firstOrNull()?.url ?: "",
        releaseDate = release_date_precision,
        timestamp = System.currentTimeMillis(),
        artistId = artistId
    )
}

@JvmName("toAlbumEntity")
fun List<AlbumResponseDto>.toEntity(artistId: String): List<AlbumEntity> {
    return map {
        AlbumEntity(
            albumId = it.id,
            artistId = artistId,
            uri = it.uri,
            name = it.name,
            imageUrl = it.images.firstOrNull()?.url ?: "",
            releaseDate = it.release_date,
            timestamp = System.currentTimeMillis(),
        )
    }
}

fun PlaylistResponseDto.toEntity() = PlaylistInfoEntity(
    playlistId = this.id,
    uri = this.uri,
    collaborative = this.collaborative,
    description = this.description,
    name = this.name,
    owner = this.owner.display_name,
    ownerId = this.owner.id,
    snapshotId = this.snapshot_id,
    totalTrack = this.tracks.total,
    imageUrl = this.images.firstOrNull()?.url,
    followers = this.followers.total ?: 0,
    timestamp = System.currentTimeMillis()
)


fun TrackItemsResponseDto.toEntity() = TrackEntity(
    trackId = this.id,
    albumId = this.album.id,
    uri = this.uri,
    durationMs = this.duration_ms,
    name = this.name,
    imageUrl = this.album.images
        .filterNotNull() // Filter out null images
        .sortedBy { it.height * it.width } // Sort images by size (smallest first)
        .let { sortedImages ->
            if (sortedImages.size > 1) {
                sortedImages[1].url // Get the 2nd smallest image
            } else {
                sortedImages.firstOrNull()?.url ?: "" // If only one image, use it
            }
        },
    previewUrl = this.preview_url,
    timestamp = System.currentTimeMillis()
)

fun UsersProfileResponseDto.toEntity(): UserEntity {
    return UserEntity(
        userId = id,
        name = display_name,
        email = email,
        uri = uri,
        image = images.firstOrNull()?.url ?: "",
        accountType = product ?: "",
        signUpdate = LocalDate.now(),
        followers = followers.total ?: 0,
        timeStamp = System.currentTimeMillis()

    )
}
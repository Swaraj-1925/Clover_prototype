package com.clovermusic.clover.data.local.dao

import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.local.entity.relations.ArtistWithAlbums
import com.clovermusic.clover.data.local.entity.relations.Playlist
import javax.inject.Inject

class Provide @Inject constructor(
    private val provideData: ProvideDataDao
) : ProvideDataDao {
    override fun getUser(): UserEntity {
        return provideData.getUser()
    }

    override fun provideAllPlaylistInfo(): List<PlaylistInfoEntity> {
        return provideData.provideAllPlaylistInfo()
    }

    override fun providePlaylistInfo(playlistId: String): PlaylistInfoEntity {
        return provideData.providePlaylistInfo(playlistId)
    }

    override fun providePlaylist(playlistId: String): Playlist {
        return provideData.providePlaylist(playlistId)
    }

    override fun provideArtistAlbum(artistId: String): ArtistWithAlbums {
        return provideData.provideArtistAlbum(artistId)
    }

    override fun provideFollowedArtists(): List<ArtistsEntity> {
        return provideData.provideFollowedArtists()
    }

    override fun provideTopArtists(): List<ArtistsEntity> {
        return provideData.provideTopArtists()
    }

}
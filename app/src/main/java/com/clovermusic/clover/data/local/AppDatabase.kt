package com.clovermusic.clover.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.clovermusic.clover.data.local.dao.AlbumDao
import com.clovermusic.clover.data.local.dao.ArtistDao
import com.clovermusic.clover.data.local.dao.PlaylistDao
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.PlaylistTrackEntity
import com.clovermusic.clover.data.providers.Converters


@Database(
    entities = [
        PlaylistInfoEntity::class,
        PlaylistTrackEntity::class,
        AlbumEntity::class,
        ArtistsEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao
}

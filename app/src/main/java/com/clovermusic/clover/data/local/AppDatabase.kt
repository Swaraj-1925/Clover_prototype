package com.clovermusic.clover.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.clovermusic.clover.data.local.dao.PlaylistDao
import com.clovermusic.clover.data.local.entity.PlaylistEntity
import com.clovermusic.clover.data.local.entity.PlaylistTrackEntity
import com.clovermusic.clover.data.providers.Converters


@Database(entities = [PlaylistEntity::class, PlaylistTrackEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
}

package com.clovermusic.clover.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.clovermusic.clover.data.local.dao.InsertDataDao
import com.clovermusic.clover.data.local.dao.ProvideDataDao
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.CollaboratorsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.TrackArtistsCrossRef


@Database(
    entities = [
        PlaylistInfoEntity::class,
        TrackEntity::class,
        AlbumEntity::class,
        ArtistsEntity::class,
        CollaboratorsEntity::class,
        UserEntity::class,
        CollaboratorsTrackCrossRef::class,
        PlaylistTrackCrossRef::class,
        TrackArtistsCrossRef::class

    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun insertDataDao(): InsertDataDao
    abstract fun provideDataDao(): ProvideDataDao
}

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun toStringList(value: String): List<String> = value.split(",").filter { it.isNotEmpty() }
}
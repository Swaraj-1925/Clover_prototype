package com.clovermusic.clover.di

import android.content.Context
import androidx.room.Room
import com.clovermusic.clover.data.local.AppDatabase
import com.clovermusic.clover.data.local.dao.InsertDataDao
import com.clovermusic.clover.data.local.dao.ProvideDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "CloverDb"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideInsertDataDao(appDatabase: AppDatabase): InsertDataDao {
        return appDatabase.insertDataDao()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase): ProvideDataDao {
        return appDatabase.provideDataDao()
    }

}

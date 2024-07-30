package com.clovermusic.clover.di

import com.clovermusic.clover.data.spotify.api.service.SpotifyAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    @Provides
    @Singleton
    @Named("authRetrofit")
    fun provideAuthRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSpotifyAuthService(@Named("authRetrofit") retrofit: Retrofit): SpotifyAuthService {
        return retrofit.create(SpotifyAuthService::class.java)
    }

}
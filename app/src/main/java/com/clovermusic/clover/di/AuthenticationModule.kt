package com.clovermusic.clover.di

import android.content.Context
import com.clovermusic.clover.data.api.spotify.service.SpotifyAuthService
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.SpotifyTokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
// Class for the Authentication of the Spotify account
@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
// Builds the instance of retrofit
    @Provides
    @Singleton
    @Named("authRetrofit")
    fun provideAuthRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
//Implements the authentication services
    @Provides
    @Singleton
    fun provideSpotifyAuthService(@Named("authRetrofit") retrofit: Retrofit): SpotifyAuthService {
        return retrofit.create(SpotifyAuthService::class.java)
    }
//Application context
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
//Instance to manage the tokens
    @Provides
    @Singleton
    fun provideSpotifyTokenManager(context: Context): SpotifyTokenManager {
        return SpotifyTokenManager(context)
    }
// Authenticates using Services and repository
    @Provides
    @Singleton
    fun provideSpotifyAuthRepository(
        tokenManager: SpotifyTokenManager,
        spotifyAuthService: SpotifyAuthService,
        context: Context
    ): SpotifyAuthRepository {
        return SpotifyAuthRepository(tokenManager, spotifyAuthService, context)
    }

}
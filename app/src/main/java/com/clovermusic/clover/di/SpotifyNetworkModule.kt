package com.clovermusic.clover.di


import com.clovermusic.clover.data.api.spotify.service.ArtistService
import com.clovermusic.clover.data.api.spotify.service.PlaylistService
import com.clovermusic.clover.data.api.spotify.service.UserService
import com.clovermusic.clover.util.SpotifyAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotifyNetworkModule {

    //    Return the OkHttpClient with the AuthInterceptor When OkHttpClient is created
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: SpotifyAuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    //    Return The build when Retrofit is created
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideArtistService(retrofit: Retrofit): ArtistService {
        return retrofit.create(ArtistService::class.java)
    }

    @Provides
    @Singleton
    fun providePlaylistService(retrofit: Retrofit): PlaylistService {
        return retrofit.create(PlaylistService::class.java)
    }

}
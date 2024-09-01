package com.clovermusic.clover.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.clovermusic.clover.presentation.composable.albumScreen.AlbumScreen
import com.clovermusic.clover.presentation.composable.artistScreen.ArtistScreen
import com.clovermusic.clover.presentation.composable.components.BottomSheetForLyrics
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar
import com.clovermusic.clover.presentation.composable.homeScreen.HomeScreen
import com.clovermusic.clover.presentation.composable.playlistScreen.PlaylistScreen
import com.clovermusic.clover.presentation.composable.searchScreen.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HomeScreenRoute) {

        composable<HomeScreenRoute> {
            HomeScreen(navController = navController)
        }
        composable<PlaylistScreenRoute> {
            val playlist: PlaylistScreenRoute = it.toRoute()
            PlaylistScreen(playlistId = playlist.id, navController = navController)
        }
        composable<PlayingSongBarRoute> {
            PlayingSongBar(navController = navController)
        }
        composable<MusicPlayerScreenRoute> {
            BottomSheetForLyrics(navController = navController)
        }
        composable<ArtistScreenRoute> {
            val artist: ArtistScreenRoute = it.toRoute()
            ArtistScreen(artistId = artist.id, navController = navController)
        }
        composable<ArtistAlbumScreenRoute> {
            val album: ArtistAlbumScreenRoute = it.toRoute()
            AlbumScreen(albumId = album.id, navController = navController)
        }
        composable<SearchScreenRoute>{
            SearchScreen(navController = navController)
        }

    }
}


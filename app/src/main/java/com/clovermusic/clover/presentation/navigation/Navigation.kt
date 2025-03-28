package com.clovermusic.clover.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.clovermusic.clover.presentation.composable.homeScreen.HomeScreen
import com.clovermusic.clover.presentation.composable.playlistScreen.PlaylistScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HomeScreenRoute) {
        composable<HomeScreenRoute> {
            HomeScreen(
                onPlaylistClick = {
                },
                onPlaylistNameClick = {
                    navController.navigate(PlaylistScreenRoute(id = it))
                }

            )
        }

        composable<PlaylistScreenRoute> {
            val playlist: PlaylistScreenRoute = it.toRoute()
            PlaylistScreen(playlistId = playlist.id)
        }

    }
}


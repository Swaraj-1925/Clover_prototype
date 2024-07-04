package com.clovermusic.clover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.domain.model.CurrentUserPlaylist
import com.clovermusic.clover.presentation.test
import com.clovermusic.clover.ui.theme.CloverTheme
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewmodel: test by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            CloverTheme {
                val artistsState by viewmodel.artistsState.collectAsState()
                when (artistsState) {
                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }

                    is Resource.Success -> {
                        val artists =
                            (artistsState as Resource.Success<List<CurrentUserPlaylist>>).data
                        ArtistsList(artists)
                    }

                    is Resource.Error -> {
//                        Text(text = (artistsState as Resource.Error).message)
                    }
                }
            }
        }


    }
}

@Composable
fun ArtistsList(artists: List<CurrentUserPlaylist>?) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        itemsIndexed(artists ?: emptyList()) { index, artist ->
            Text(text = "${index + 1}. ${artist.name}")
        }
    }
}

package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.clovermusic.clover.R

data class ThemedIcons(
    val addIcon: Int,
    val shareIcon: Int,
    val playIcon: Int,
    val pauseIcon: Int,
    val shuffleButtonInactive: Int,
    val shuffleButtonActive: Int,
    val previousButton: Int,
    val nextButton: Int,
    val repeatButtonInactive: Int,
    val repeatButtonActive: Int,
    val repeatButtonActive1: Int,
    val fastForwardButton: Int,
    val fastBackwardButton: Int,
    val moreIcon: Int,
)

@Composable
fun getThemedIcons(): ThemedIcons {
    val isDarkTheme = isSystemInDarkTheme()
    return if (isDarkTheme) {
        ThemedIcons(
            addIcon = R.drawable.add_white,
            shareIcon = R.drawable.share_white,
            playIcon = R.drawable.play_white,
            pauseIcon = R.drawable.pause_white,
            shuffleButtonInactive = R.drawable.shuffle_white_inactive,
            shuffleButtonActive = R.drawable.shuffle_white_active,
            previousButton = R.drawable.previous_white,
            nextButton = R.drawable.next_white,
            repeatButtonInactive = R.drawable.repeat_white_inactive,
            repeatButtonActive = R.drawable.repeat_white_active,
            repeatButtonActive1 = R.drawable.repeat_white_active_2,
            fastForwardButton = R.drawable.fast_forward_white,
            fastBackwardButton = R.drawable.fast_backward_white,
            moreIcon = R.drawable.more_white
        )
    } else {
        ThemedIcons(
            addIcon = R.drawable.add_black,
            shareIcon = R.drawable.share_black,
            playIcon = R.drawable.play_black,
            pauseIcon = R.drawable.pause_black,
            shuffleButtonActive = R.drawable.shuffle_black_active,
            shuffleButtonInactive = R.drawable.shuffle_black_inactive,
            previousButton = R.drawable.previous_black,
            nextButton = R.drawable.next_white,
            repeatButtonActive = R.drawable.repeat_black_active,
            repeatButtonInactive = R.drawable.repeat_black_inactive,
            repeatButtonActive1 = R.drawable.repeat_black_active_2,
            fastForwardButton = R.drawable.fast_forward_black,
            fastBackwardButton = R.drawable.fast_backward_black,
            moreIcon = R.drawable.more_black
        )
    }
}

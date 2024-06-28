package com.clovermusic.clover.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkPrimaryOn,
    secondary = DarkSecondary,
    tertiary = DarkTertiary,
    primaryContainer = DarkPrimaryContainer,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkSecondaryContainerOn,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
    surface = DarkSurface,
    onSurface = DarkSurafaceOn,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = SurfaceVariantOn,
    error = Error
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightPrimaryOn,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    primaryContainer = LightPrimaryContainer,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightSecondaryContainerOn,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
    surface = LightSurface,
    onSurface = LightSurfaceOn,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = SurfaceVariantOn,
    error = Error
)


@Composable
fun CloverTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
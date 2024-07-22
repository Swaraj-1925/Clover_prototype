package com.clovermusic.clover.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.clovermusic.clover.R


// App Name title
val Cabin = FontFamily(
    Font(R.font.cabin_variable)
)

// Any Description
val Jost = FontFamily(
    Font(R.font.jost_variable)
)

// Labels
val RobotoMedium = FontFamily(
    Font(R.font.roboto_medium)
)
val RobotoReguler = FontFamily(
    Font(R.font.roboto_regular)
)

// Name of Artists and User
val IbmPlexSans = FontFamily(
    Font(R.font.ibm_plex_sans_regular)
)

// Songs and album title
val Arimo = FontFamily(
    Font(R.font.arimo_variable)
)


// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp // changed
    ),
    titleSmall = TextStyle(
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = Jost,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Jost,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp  // changed
    ),
    bodySmall = TextStyle(
        fontFamily = Jost,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp  // changed
    ),

    headlineLarge = TextStyle(
        fontFamily = Arimo,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Arimo,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Arimo,
        fontWeight = FontWeight.Bold,
        fontSize = 8.sp
    ),
    labelLarge = TextStyle(
        fontFamily = RobotoMedium,
        fontSize = 20.sp
    ),

    labelMedium = TextStyle(
        fontFamily = RobotoReguler,
        fontSize = 14.sp
    ),

    labelSmall = TextStyle(
        fontFamily = RobotoReguler,
        fontSize = 12.sp
    )
)

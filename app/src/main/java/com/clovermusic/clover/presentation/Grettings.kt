import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getDominantColorFromImageUrl(context: Context, imageUrl: String): Color {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(false) // Disable hardware bitmaps.
        .build()

    val result = (loader.execute(request) as SuccessResult).drawable
    val bitmap = (result as BitmapDrawable).bitmap

    return withContext(Dispatchers.Default) {
        val palette = Palette.from(bitmap).generate()
        val dominantColor = palette.getDominantColor(android.graphics.Color.BLACK)
        Color(dominantColor)
    }
}


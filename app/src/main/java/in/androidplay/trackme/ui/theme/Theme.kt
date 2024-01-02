package `in`.androidplay.trackme.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun TrackMeTheme(
    isDynamicColor: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = if (darkTheme) {
        if (dynamicColor) {
            dynamicDarkColorScheme(context)
        } else {
            darkColorPalette
        }
    } else {
        if (dynamicColor) {
            dynamicLightColorScheme(LocalContext.current)
        } else {
            lightColorPalette
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

private val darkColorPalette = darkColorScheme(

)

private val lightColorPalette = lightColorScheme(

)
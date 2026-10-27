package com.nothing.bitlauncher.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NothingWhite,
    secondary = NothingRed,
    tertiary = NothingGrayText,
    background = NothingBlack,
    surface = NothingDarkGray,
    onPrimary = NothingBlack,
    onSecondary = NothingWhite,
    onBackground = NothingWhite,
    onSurface = NothingWhite,
    outline = NothingBorder
)

@Composable
fun NothingBitLauncherTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = NothingBlack.toArgb()
            window.navigationBarColor = NothingBlack.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

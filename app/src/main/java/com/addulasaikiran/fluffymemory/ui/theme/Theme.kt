package com.addulasaikiran.fluffymemory.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

enum class DialerThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2563EB),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE7EEFF),
    onPrimaryContainer = Color(0xFF153A86),
    secondary = Color(0xFF53627A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFEEF1F5),
    onSecondaryContainer = Color(0xFF263244),
    background = Color(0xFFF7F8FA),
    onBackground = Color(0xFF151922),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF151922),
    surfaceVariant = Color(0xFFEEF1F5),
    onSurfaceVariant = Color(0xFF687181),
    outline = Color(0xFF8A93A1),
    outlineVariant = Color(0xFFE4E7EC),
    error = Color(0xFFD92D3F),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFE9EC),
    onErrorContainer = Color(0xFF7F1423)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF82A9FF),
    onPrimary = Color(0xFF102B5C),
    primaryContainer = Color(0xFF1C3158),
    onPrimaryContainer = Color(0xFFD9E5FF),
    secondary = Color(0xFFB5C1D4),
    onSecondary = Color(0xFF263244),
    secondaryContainer = Color(0xFF202938),
    onSecondaryContainer = Color(0xFFE0E7F2),
    background = Color(0xFF0D1118),
    onBackground = Color(0xFFF4F6FA),
    surface = Color(0xFF151B24),
    onSurface = Color(0xFFF4F6FA),
    surfaceVariant = Color(0xFF202938),
    onSurfaceVariant = Color(0xFFA5AFBE),
    outline = Color(0xFF758195),
    outlineVariant = Color(0xFF2A3442),
    error = Color(0xFFFF6476),
    onError = Color(0xFF450A14),
    errorContainer = Color(0xFF641D2A),
    onErrorContainer = Color(0xFFFFD9DE)
)

@Composable
fun DialerTheme(
    themeMode: DialerThemeMode = DialerThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val useDarkTheme = when (themeMode) {
        DialerThemeMode.SYSTEM -> isSystemInDarkTheme()
        DialerThemeMode.LIGHT -> false
        DialerThemeMode.DARK -> true
    }

    CompositionLocalProvider(
        LocalDialerColors provides if (useDarkTheme) DarkDialerColors else LightDialerColors
    ) {
        MaterialTheme(
            colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme,
            typography = DialerTypography,
            shapes = DialerShapes,
            content = content
        )
    }
}

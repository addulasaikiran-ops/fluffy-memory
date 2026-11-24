package com.addulasaikiran.fluffymemory.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class DialerColors(
    val surfaceSubtle: Color,
    val textSecondary: Color,
    val accentSoft: Color,
    val success: Color,
    val danger: Color,
    val divider: Color
)

val LightDialerColors = DialerColors(
    surfaceSubtle = Color(0xFFEEF1F5),
    textSecondary = Color(0xFF687181),
    accentSoft = Color(0xFFE7EEFF),
    success = Color(0xFF16845B),
    danger = Color(0xFFD92D3F),
    divider = Color(0xFFE4E7EC)
)

val DarkDialerColors = DialerColors(
    surfaceSubtle = Color(0xFF202938),
    textSecondary = Color(0xFFA5AFBE),
    accentSoft = Color(0xFF1C3158),
    success = Color(0xFF55C79A),
    danger = Color(0xFFFF6476),
    divider = Color(0xFF2A3442)
)

val LocalDialerColors = staticCompositionLocalOf { LightDialerColors }

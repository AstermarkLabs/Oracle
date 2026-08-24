package dev.astermark.oracle.ui.theme

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

private val LightColorScheme = lightColorScheme(
    primary = OracleAccentLight,
    background = OracleBackgroundLight,
    surface = OracleBackgroundLight,
    onBackground = OracleTextPrimaryLight,
    onSurface = OracleTextPrimaryLight,
    onSurfaceVariant = OracleTextSecondaryLight,
    outlineVariant = OracleProgressInactiveLight
)

private val DarkColorScheme = darkColorScheme(
    primary = OracleAccent,
    background = OracleBackground,
    surface = OracleBackground,
    onBackground = OracleTextPrimary,
    onSurface = OracleTextPrimary,
    onSurfaceVariant = OracleTextSecondary,
    outlineVariant = OracleProgressInactive
)

@Composable
fun OracleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
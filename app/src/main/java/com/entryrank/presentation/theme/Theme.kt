package com.entryrank.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * EntryRank Theme
 * Colors match exactly with website's OKLCH color scheme
 * Light theme matches :root, Dark theme matches .dark
 */

private val DarkColorScheme = darkColorScheme(
    // Primary (in dark mode uses light gray, not green)
    primary = PrimaryDark,  // oklch(0.929 0.013 255.508)
    onPrimary = PrimaryForegroundDark,
    primaryContainer = SecondaryDark,
    onPrimaryContainer = SecondaryForegroundDark,
    
    // Secondary
    secondary = SecondaryDark,  // oklch(0.279 0.041 260.031)
    onSecondary = SecondaryForegroundDark,
    secondaryContainer = MutedDark,
    onSecondaryContainer = MutedForegroundDark,
    
    // Tertiary (using accent colors)
    tertiary = AccentDark,
    onTertiary = AccentForegroundDark,
    
    // Error
    error = DestructiveDark,  // oklch(0.704 0.191 22.216)
    onError = DestructiveForegroundDark,
    errorContainer = DestructiveDark,
    onErrorContainer = DestructiveForegroundDark,
    
    // Background
    background = BackgroundDark,  // oklch(0.129 0.042 264.695) = #0F172A
    onBackground = ForegroundDark,
    
    // Surface (cards, etc)
    surface = CardDark,  // oklch(0.208 0.042 265.755) = #1E293B
    onSurface = CardForegroundDark,
    surfaceVariant = MutedDark,
    onSurfaceVariant = MutedForegroundDark,
    
    // Outline
    outline = BorderDark,  // White with 10% opacity
    outlineVariant = InputDark,
    
    // Surface tints
    surfaceTint = PrimaryDark,
    inverseSurface = ForegroundDark,
    inverseOnSurface = BackgroundDark,
    inversePrimary = Primary,  // Green color in dark mode as accent
    
    // Scrim
    scrim = Color(0xFF000000)
)

private val LightColorScheme = lightColorScheme(
    // Primary (EntryRank Green #16A34A)
    primary = Primary,  // oklch(0.527 0.154 150.069) = #16A34A
    onPrimary = PrimaryForeground,
    primaryContainer = Secondary,  // Light green tint
    onPrimaryContainer = SecondaryForeground,
    
    // Secondary
    secondary = Secondary,  // oklch(0.957 0.037 150.069) = very light green
    onSecondary = SecondaryForeground,
    secondaryContainer = Accent,
    onSecondaryContainer = AccentForeground,
    
    // Tertiary (using accent)
    tertiary = Accent,
    onTertiary = AccentForeground,
    
    // Error
    error = Destructive,  // oklch(0.577 0.245 27.325) = red
    onError = DestructiveForeground,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Destructive,
    
    // Background
    background = Background,  // oklch(1 0 0) = pure white
    onBackground = Foreground,  // oklch(0.129 0.042 264.695)
    
    // Surface (cards, etc)
    surface = Card,  // White
    onSurface = CardForeground,
    surfaceVariant = Muted,  // oklch(0.968 0.007 247.896)
    onSurfaceVariant = MutedForeground,
    
    // Outline (borders)
    outline = Border,  // oklch(0.929 0.013 255.508) = #E2E8F0
    outlineVariant = Input,
    
    // Surface tints
    surfaceTint = Primary,
    inverseSurface = Foreground,
    inverseOnSurface = Background,
    inversePrimary = Color(0xFF34D399),  // Light green for dark surface
    
    // Scrim
    scrim = Color(0xFF000000)
)

@Composable
fun EntryRankTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled by default to match website exactly
    dynamicColor: Boolean = false,  // Changed from true to false
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Only use dynamic colors if explicitly enabled
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Use EntryRank green (#16A34A) for status bar in light mode
            window.statusBarColor = if (darkTheme) {
                colorScheme.surface.toArgb()  // Dark surface color
            } else {
                Primary.toArgb()  // EntryRank green
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

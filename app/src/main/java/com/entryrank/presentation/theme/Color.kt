package com.entryrank.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * EntryRank Color System
 * Converted from website's OKLCH color scheme to match exactly
 * Website uses: oklch(0.527 0.154 150.069) for primary green
 */

// Light Theme Colors (matches website :root)
// Primary - EntryRank Green (oklch(0.527 0.154 150.069) = #16a34a)
val Primary = Color(0xFF16A34A)  // Same as website theme_color
val PrimaryForeground = Color(0xFFFFFFFF)

// Background (oklch(1 0 0) = pure white)
val Background = Color(0xFFFFFFFF)
val Foreground = Color(0xFF0F172A)  // oklch(0.129 0.042 264.695)

// Card (oklch(1 0 0) = white)
val Card = Color(0xFFFFFFFF)
val CardForeground = Color(0xFF0F172A)

// Secondary (oklch(0.957 0.037 150.069) - light green tint)
val Secondary = Color(0xFFF0FDF4)  // Very light green
val SecondaryForeground = Color(0xFF166534)  // oklch(0.3 0.09 150.069)

// Muted (oklch(0.968 0.007 247.896) - light gray)
val Muted = Color(0xFFF8FAFC)
val MutedForeground = Color(0xFF64748B)  // oklch(0.554 0.046 257.417)

// Accent (same as secondary - light green)
val Accent = Color(0xFFF0FDF4)
val AccentForeground = Color(0xFF166534)

// Destructive/Error (oklch(0.577 0.245 27.325) - red)
val Destructive = Color(0xFFEF4444)
val DestructiveForeground = Color(0xFFFEF2F2)  // oklch(0.984 0.003 247.858)

// Border (oklch(0.929 0.013 255.508) - light gray)
val Border = Color(0xFFE2E8F0)
val Input = Color(0xFFE2E8F0)

// Ring (same as primary)
val Ring = Color(0xFF16A34A)

// Dark Theme Colors (matches website .dark)
// Background (oklch(0.129 0.042 264.695) - very dark blue)
val BackgroundDark = Color(0xFF0F172A)
val ForegroundDark = Color(0xFFFAFAFA)  // oklch(0.984 0.003 247.858)

// Card (oklch(0.208 0.042 265.755) - dark blue-gray)
val CardDark = Color(0xFF1E293B)
val CardForegroundDark = Color(0xFFFAFAFA)

// Primary in dark mode (oklch(0.929 0.013 255.508) - light gray)
val PrimaryDark = Color(0xFFE2E8F0)
val PrimaryForegroundDark = Color(0xFF1E293B)

// Secondary in dark (oklch(0.279 0.041 260.031) - dark gray)
val SecondaryDark = Color(0xFF334155)
val SecondaryForegroundDark = Color(0xFFFAFAFA)

// Muted in dark (oklch(0.279 0.041 260.031))
val MutedDark = Color(0xFF334155)
val MutedForegroundDark = Color(0xFF94A3B8)  // oklch(0.704 0.04 256.788)

// Accent in dark
val AccentDark = Color(0xFF334155)
val AccentForegroundDark = Color(0xFFFAFAFA)

// Destructive in dark (oklch(0.704 0.191 22.216))
val DestructiveDark = Color(0xFFF87171)
val DestructiveForegroundDark = Color(0xFFFAFAFA)

// Border in dark (oklch(1 0 0 / 10%) - white with 10% opacity)
val BorderDark = Color(0x1AFFFFFF)
val InputDark = Color(0x26FFFFFF)  // oklch(1 0 0 / 15%)

// Ring in dark
val RingDark = Color(0xFF475569)  // oklch(0.551 0.027 264.364)

// Additional colors for consistency
val Success = Color(0xFF10B981)  // Green for correct answers
val SuccessDark = Color(0xFF34D399)

val Warning = Color(0xFFF59E0B)  // Yellow for warnings
val WarningDark = Color(0xFFFBBF24)

val Error = Color(0xFFEF4444)  // Red for errors
val ErrorDark = Color(0xFFF87171)

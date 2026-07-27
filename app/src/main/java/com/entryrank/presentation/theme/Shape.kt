package com.entryrank.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * EntryRank Shape System
 * Matches website's border radius: --radius: 0.625rem = 10px
 * 
 * Website uses:
 * - sm: radius - 4px = 6px
 * - md: radius - 2px = 8px
 * - lg: radius = 10px (base)
 * - xl: radius + 4px = 14px
 * - 2xl: radius + 8px = 18px
 */

val Shapes = Shapes(
    // Extra small - 4dp
    extraSmall = RoundedCornerShape(4.dp),
    
    // Small - 6dp (matches website sm: 0.625rem - 4px)
    small = RoundedCornerShape(6.dp),
    
    // Medium - 8dp (matches website md: 0.625rem - 2px)
    medium = RoundedCornerShape(8.dp),
    
    // Large - 10dp (matches website lg: 0.625rem base)
    large = RoundedCornerShape(10.dp),
    
    // Extra large - 14dp (matches website xl: 0.625rem + 4px)
    extraLarge = RoundedCornerShape(14.dp)
)

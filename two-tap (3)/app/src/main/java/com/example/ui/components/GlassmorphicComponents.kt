package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// premium gradients and colors
val NeonPurple = Color(0xFF9B5DE5)
val NeonPink = Color(0xFFF15BB5)
val NeonTeal = Color(0xFF00F5D4)
val ElectricBlue = Color(0xFF3B82F6)

val SpaceDarkBg = Color(0xFF0C0D14)
val SpaceDarkCard = Color(0xFF161726)

val LightSoftBg = Color(0xFFF8FAFC)
val LightSoftCard = Color(0xFFFFFFFF)

@Composable
fun AtmosphericBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = isSystemInDarkTheme()
    
    val bgBrush = if (isDark) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF090A10),
                Color(0xFF10121E)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFF1F5F9),
                Color(0xFFE2E8F0)
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgBrush)
            .drawBehind {
                // Draw decorative glowing space dust/aura circles to give that glassmorphic glow
                if (isDark) {
                    drawCircle(
                        color = NeonPurple.copy(alpha = 0.08f),
                        radius = size.width * 0.6f,
                        center = Offset(size.width * 0.1f, size.height * 0.2f)
                    )
                    drawCircle(
                        color = NeonTeal.copy(alpha = 0.06f),
                        radius = size.width * 0.5f,
                        center = Offset(size.width * 0.9f, size.height * 0.7f)
                    )
                } else {
                    drawCircle(
                        color = ElectricBlue.copy(alpha = 0.06f),
                        radius = size.width * 0.7f,
                        center = Offset(size.width * 0.1f, size.height * 0.1f)
                    )
                    drawCircle(
                        color = NeonPink.copy(alpha = 0.04f),
                        radius = size.width * 0.5f,
                        center = Offset(size.width * 0.8f, size.height * 0.8f)
                    )
                }
            }
    ) {
        content()
    }
}

@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: RoundedCornerShape = RoundedCornerShape(24.dp),
    borderWidth: Dp = 1.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val isDark = isSystemInDarkTheme()
    
    // Glassmorphic transparency colors
    val containerColor = if (isDark) {
        Color(0xFF1E1F35).copy(alpha = 0.55f)
    } else {
        Color.White.copy(alpha = 0.75f)
    }
    
    val borderColor = if (isDark) {
        Color.White.copy(alpha = 0.12f)
    } else {
        Color.Black.copy(alpha = 0.07f)
    }

    val cardModifier = if (onClick != null) {
        modifier.clickable(onClick = onClick)
    } else {
        modifier
    }

    Card(
        modifier = cardModifier,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(borderWidth, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isDark) 0.dp else 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}

@Composable
fun GlassmorphicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    content: @Composable RowScope.() -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val baseColor = if (isDark) NeonPurple else ElectricBlue
    
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = baseColor,
            contentColor = Color.White,
            disabledContainerColor = baseColor.copy(alpha = 0.4f),
            disabledContentColor = Color.White.copy(alpha = 0.6f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

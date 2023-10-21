package org.jetbrains.jewel

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Immutable
@GenerateDataFunctions
class ThemeDefinition(
    val isDark: Boolean,
    val globalColors: GlobalColors,
    val globalMetrics: GlobalMetrics,
    val defaultTextStyle: TextStyle,
    val contentColor: Color,
    val colorPalette: ThemeColorPalette,
    val iconData: ThemeIconData,
)
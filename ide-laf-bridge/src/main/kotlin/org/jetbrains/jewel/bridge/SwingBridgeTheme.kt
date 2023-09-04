package org.jetbrains.jewel.bridge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.intellij.openapi.components.service
import org.jetbrains.jewel.ExperimentalJewelApi
import org.jetbrains.jewel.themes.intui.standalone.IntUiTheme

private val bridgeService = service<SwingBridgeService>()

@ExperimentalJewelApi
@Composable
fun SwingBridgeTheme(content: @Composable () -> Unit) {
    val themeData by bridgeService.currentBridgeThemeData.collectAsState()

    // TODO handle non-Int UI themes, too
    IntUiTheme(themeData.themeDefinition, themeData.componentStyling, swingCompatMode = true, content)
}
package com.momentolabs.app.security.cleanarchitecture.models

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItems(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val badgeCount: Int? = null
)

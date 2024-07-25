package com.momentolabs.app.security.cleanarchitecture.navigation

enum class Screen {
    Splash,
    Home,
    CreateNotes
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.Splash.name)
    data object Home : NavigationItem(Screen.Home.name)
    data object CreateNotes : NavigationItem(Screen.CreateNotes.name)
}
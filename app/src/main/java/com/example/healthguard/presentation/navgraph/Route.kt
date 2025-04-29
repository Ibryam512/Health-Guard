package com.example.healthguard.presentation.navgraph

sealed class Route (val route: String) {
    object OnBoardingScreen : Route("onBoardingScreen")
    object HomeScreen : Route("homeScreen")
    object ContactsScreen : Route("contactsScreen")
    object MessageScreen : Route("messageScreen")
    object AppStartNavigation : Route("appStartNavigation")
    object AppNavigation : Route("appNavigation")
}
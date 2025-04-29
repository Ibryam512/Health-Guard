package com.example.healthguard.presentation.navigationbar

import androidx.annotation.DrawableRes
import com.example.healthguard.R
import com.example.healthguard.presentation.navgraph.Route

data class NavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
    val route: String
)

val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        icon = R.drawable.ic_sos_icon,
        route = Route.HomeScreen.route
    ),
    NavigationItem(
        title = "Contacts",
        icon = R.drawable.ic_contacts_icon,
        route = Route.ContactsScreen.route
    ),
    NavigationItem(
        title = "Message",
        icon = R.drawable.ic_chat_icon,
        route = Route.MessageScreen.route
    )
)

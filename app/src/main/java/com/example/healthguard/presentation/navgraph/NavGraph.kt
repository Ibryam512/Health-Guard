package com.example.healthguard.presentation.navgraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.healthguard.presentation.contacts.ContactsScreen
import com.example.healthguard.presentation.contacts.ContactsViewModel
import com.example.healthguard.presentation.home.HomeScreen
import com.example.healthguard.presentation.home.HomeViewModel
import com.example.healthguard.presentation.message.MessageScreen
import com.example.healthguard.presentation.message.MessageViewModel
import com.example.healthguard.presentation.onboarding.OnBoardingScreen
import com.example.healthguard.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            startDestination = Route.OnBoardingScreen.route,
            route = Route.AppStartNavigation.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    onEvent = onBoardingViewModel::onEvent
                )
            }
        }
        navigation(
            startDestination = Route.HomeScreen.route,
            route = Route.AppNavigation.route
        ) {
            composable(route = Route.HomeScreen.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    onClick = homeViewModel::sendSOSMessage
                )
            }
            composable(route = Route.ContactsScreen.route) {
                val contactsViewModel: ContactsViewModel = hiltViewModel()
                ContactsScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    viewModel = contactsViewModel
                )
            }
            composable(route = Route.MessageScreen.route) {
                val messageViewModel: MessageViewModel = hiltViewModel()
                MessageScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    viewModel = messageViewModel
                )
            }
        }
    }
}
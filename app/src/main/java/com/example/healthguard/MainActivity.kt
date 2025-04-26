package com.example.healthguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.healthguard.presentation.navgraph.NavGraph
import com.example.healthguard.presentation.navgraph.Route
import com.example.healthguard.presentation.navigationbar.BottomNavigationBar
import com.example.healthguard.ui.theme.HealthGuardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }
        setContent {
            HealthGuardTheme {
                val navController = rememberNavController()

                if (viewModel.showNavBar) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier
                            .padding(innerPadding)
                            .background(color = MaterialTheme.colorScheme.background)) {
                            NavGraph(
                                navController = navController,
                                startDestination = Route.AppNavigation.route
                            )
                        }
                    }
                }
                else {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)) {
                        NavGraph(
                            navController = navController,
                            startDestination = Route.AppStartNavigation.route
                        )
                    }
                }
            }
        }
    }
}

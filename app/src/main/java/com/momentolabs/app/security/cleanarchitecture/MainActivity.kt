package com.momentolabs.app.security.cleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.momentolabs.app.security.cleanarchitecture.navigation.NavigationItem
import com.momentolabs.app.security.cleanarchitecture.screens.SplashScreen
import com.momentolabs.app.security.cleanarchitecture.screens.createNotes.CreateNotes
import com.momentolabs.app.security.cleanarchitecture.screens.home.HomeScreen
import com.momentolabs.app.security.cleanarchitecture.ui.theme.CleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            CleanArchitectureTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screens(navHostController = rememberNavController())
                }

            }
        }
    }



    @Composable
    fun Screens(
        modifier: Modifier = Modifier, navHostController: NavHostController,
        startDestination: String = NavigationItem.Splash.route
    ) {
        NavHost(
            modifier = modifier,
            navController = navHostController,
            startDestination = startDestination
        ) {

            composable(route = NavigationItem.Splash.route) {
                SplashScreen(navHostController)
            }


            composable(route = NavigationItem.Home.route) {
                HomeScreen(navHostController)
            }

            composable(route = NavigationItem.CreateNotes.route) {
                CreateNotes(navHostController)
            }


        }
    }


}



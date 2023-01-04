package org.includejoe.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.includejoe.instagramclone.presentation.SplashScreen
import org.includejoe.instagramclone.presentation.authentication.AuthenticationViewModel
import org.includejoe.instagramclone.presentation.authentication.LoginScreen
import org.includejoe.instagramclone.presentation.authentication.SignUpScreen
import org.includejoe.instagramclone.presentation.main.FeedsScreen
import org.includejoe.instagramclone.ui.theme.InstagramCloneTheme
import org.includejoe.instagramclone.util.Screens

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val authViewModel: AuthenticationViewModel = hiltViewModel()
                    Navigation(navController, authViewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, authViewModel: AuthenticationViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen()
        }
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(route = Screens.FeedsScreen.route) {
            FeedsScreen()
        }
    }
}
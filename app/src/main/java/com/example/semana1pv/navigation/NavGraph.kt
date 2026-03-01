package com.example.semana1pv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semana1pv.ui.screens.HomeMenuScreen
import com.example.semana1pv.ui.screens.EscribirScreen
import com.example.semana1pv.ui.screens.HablarScreen
import com.example.semana1pv.ui.screens.LoginScreen
import com.example.semana1pv.ui.screens.RecuperarScreen
import com.example.semana1pv.ui.screens.RegistroScreen
import com.example.semana1pv.ui.screens.SpashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Registro : Screen("registro")
    object Recuperar : Screen("recuperar")
    object Home : Screen("home")
    object Escribir : Screen("escribir")
    object Hablar : Screen("hablar")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SpashScreen(
                onStartClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegistroClick = { navController.navigate(Screen.Registro.route) },
                onForgotClick = { navController.navigate(Screen.Recuperar.route) }
            )
        }

        composable(Screen.Registro.route) {
            RegistroScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Registro.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Recuperar.route) {
            RecuperarScreen(
                onBackToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Recuperar.route) { inclusive = true }
                    }
                }
            )
        }


        composable(Screen.Escribir.route) {
            EscribirScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Hablar.route) {
            HablarScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Home.route) {
            HomeMenuScreen(
                onEscribir = { navController.navigate(Screen.Escribir.route) },
                onHablar = { navController.navigate(Screen.Hablar.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

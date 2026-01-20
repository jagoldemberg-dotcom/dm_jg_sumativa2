package com.example.semana1pv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.semana1pv.navigation.NavGraph
import com.example.semana1pv.ui.theme.Semana1pvTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Semana1pvTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

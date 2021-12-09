package com.vikayarska.kotlinapplicationcompose.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.vikayarska.kotlinapplicationcompose.presentation.AppNavigation
import com.vikayarska.kotlinapplicationcompose.presentation.ui.BasicsApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

//TODO: fix theme
@AndroidEntryPoint
class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BasicsApplicationTheme {
                AppNavigation(navController = navController)

            }
        }
    }
}
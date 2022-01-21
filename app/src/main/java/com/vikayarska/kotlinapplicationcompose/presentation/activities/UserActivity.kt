package com.vikayarska.kotlinapplicationcompose.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.compose.rememberNavController
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.presentation.AppNavigation
import com.vikayarska.kotlinapplicationcompose.presentation.ui.BasicsApplicationTheme
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersProfileViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class UserActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun userProfileViewModelFactory(): UsersProfileViewModel.Factory
    }

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

@Composable
fun SimpleCircularProgressIndicator() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.loaderSize))
        )
    }
}
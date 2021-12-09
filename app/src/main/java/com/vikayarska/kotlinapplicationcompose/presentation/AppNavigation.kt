package com.vikayarska.kotlinapplicationcompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.presentation.fragments.UserProfileScreen
import com.vikayarska.kotlinapplicationcompose.presentation.fragments.UsersListScreen

@Composable
internal fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.UserList.route
    ) {
        composable(
            Routes.UserList.route,
            arguments = listOf(navArgument(USER) {
                type = NavType.SerializableType(AppUser::class.java)
            }
            )
        ) { UsersListScreen(navController = navController) }
        composable(
            Routes.UserProfile.route
        )
        {
            val user =
                navController.previousBackStackEntry?.arguments?.getSerializable(USER) as? AppUser
            UserProfileScreen(user)
        }
    }
}

const val USER = "user"

sealed class Routes(val route: String) {
    object UserList : Routes("userslist")
    object UserProfile : Routes("userprofile")
}
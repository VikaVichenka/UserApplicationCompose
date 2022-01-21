package com.vikayarska.kotlinapplicationcompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vikayarska.kotlinapplicationcompose.presentation.Routes.UserProfile.ARG_USER_ID
import com.vikayarska.kotlinapplicationcompose.presentation.fragments.UserProfileScreen
import com.vikayarska.kotlinapplicationcompose.presentation.fragments.UsersListScreen
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersProfileViewModel
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.assistedViewModel

@Composable
internal fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.UserList.route
    ) {
        composable(
            Routes.UserList.route
        ) { UsersListScreen(navController = navController) }
        composable(
            Routes.UserProfile.route,
            arguments = listOf(navArgument(ARG_USER_ID) { type = NavType.IntType })
        )
        {
            UserProfileScreen(assistedViewModel {
                UsersProfileViewModel.provideFactory(
                    userProfileViewModelFactory(),
                    it.arguments?.getInt(ARG_USER_ID)
                )
            })
        }
    }
}


sealed class Routes(val route: String) {
    object UserList : Routes("userslist")
    object UserProfile : Routes("userprofile/{userId}") {
        fun route(userId: Int) = "userprofile/$userId"

        const val ARG_USER_ID: String = "userId"
    }
}
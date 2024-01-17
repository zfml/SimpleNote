package com.zfml.simplenote.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zfml.simplenote.domain.util.Constants.WRITE_NOTE_KEY
import com.zfml.simplenote.presentation.screen.notes.HomeScreen
import com.zfml.simplenote.presentation.screen.write.WriteScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeRoute(
            navigateToWriteScreen = {
                navController.navigate(Screen.Write.route)
            },
            navigateToWriteScreenArg = {
                navController.navigate(Screen.Write.passById(it))
            }
        )
        writeRoute(
            navigateToHome = {
                navController.popBackStack()
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeRoute(
    navigateToWriteScreen: () -> Unit,
    navigateToWriteScreenArg: (Int) -> Unit,
) {
    composable(route = Screen.Home.route) {

        HomeScreen(
            navigateToWriteScreen = navigateToWriteScreen,
            navigateToWriteScreenWithArg = navigateToWriteScreenArg
        )
    }
}

fun NavGraphBuilder.writeRoute(
    navigateToHome: () -> Unit,
) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(
            navArgument(name = WRITE_NOTE_KEY) {
                type = NavType.IntType
                defaultValue = -1

            }
        )
    ) {
        WriteScreen(
            navigateToHome = navigateToHome
        )
    }
}
package gg.dsepractice.chinese.frontend.sample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import gg.dsepractice.chinese.frontend.sample.ExerciseScreen
import gg.dsepractice.chinese.frontend.sample.HomeScreen
import gg.dsepractice.chinese.frontend.sample.LearnScreen
import gg.dsepractice.chinese.frontend.sample.ui.screen.LoginScreen
import gg.dsepractice.chinese.frontend.sample.ui.theme.NavRoute


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login.path
    ) {
        addLoginScreen(navController, this)

        addHomeScreen(navController, this)

        addLearnScreen(navController, this)

        addExerciseScreen(navController, this)
    }
}


private fun addLoginScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Login.path) {
        LoginScreen(
            navigateToHome = {
                navController.navigate(NavRoute.Home.path)
            }
        )
    }
}

private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
)  {
    navGraphBuilder.composable(route = NavRoute.Home.path) {

        HomeScreen(
            navigateToLearn = { id, showDetails ->
                navController.navigate(NavRoute.LearnMode.withArgs(id.toString(), showDetails.toString()))
            },
            navigateToExercise = { id ->
                navController.navigate(NavRoute.LearnMode.withArgs(id.toString()))
            },
            //popBackStack = { navController.popBackStack() }
            //popUpToLogin= { popUpToLogin(navController) },
        )
    }
}



private fun addLearnScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.LearnMode.withArgsFormat(NavRoute.LearnMode.id, NavRoute.LearnMode.showDetails),
        arguments = listOf(
            navArgument(NavRoute.LearnMode.id) {
                type = NavType.IntType
            }
            ,
            navArgument(NavRoute.LearnMode.showDetails) {
                type = NavType.BoolType
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        LearnScreen(
            id = args?.getInt(NavRoute.LearnMode.id)!!,
            showDetails = args.getBoolean(NavRoute.LearnMode.showDetails),
            popBackStack = { navController.popBackStack() },
            //popUpToLogin = { popUpToLogin(navController) }
        )
    }
}

private fun addExerciseScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.ExerciseMode.withArgsFormat(NavRoute.ExerciseMode.id),
        arguments = listOf(
            navArgument(NavRoute.ExerciseMode.id) {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        ExerciseScreen(
            id = args?.getInt(NavRoute.ExerciseMode.id)!!,
            popBackStack = { navController.popBackStack() },
            //popUpToLogin = { popUpToLogin(navController) }
        )
    }
}
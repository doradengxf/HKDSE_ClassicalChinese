package gg.dsepractice.chinese.frontend.sample.ui.navigation

//import gg.dsepractice.chinese.frontend.sample.ScheduleScreen
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
import gg.dsepractice.chinese.frontend.sample.ui.screen.ModeScreen
import gg.dsepractice.chinese.frontend.sample.ui.screen.ResultScreen
import gg.dsepractice.chinese.frontend.sample.ui.theme.NavRoute


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login.path
    ) {
        addLoginScreen(navController, this)

        addHomeScreen(navController, this)

        addModeScreen(navController,this)

        addLearnScreen(navController, this)

        addExerciseScreen(navController, this)

        addResultScreen(navController,this)

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
            navigateToMode = {
                navController.navigate(NavRoute.Mode.path)
            },
            navigateToLearn = { id, showDetails ->
                navController.navigate(NavRoute.LearnMode.withArgs(id.toString(), showDetails.toString()))
            },
            navigateToExercise = {
                navController.navigate(NavRoute.ExerciseMode.path)
            },
            //popBackStack = { navController.popBackStack() }
            //popUpToLogin= { popUpToLogin(navController) },
        )
    }
}

private fun addModeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
){
    navGraphBuilder.composable(route = NavRoute.Mode.path) {

        ModeScreen(
            popBackStack = { navController.popBackStack() },
            navigateToLearn = { id, showDetails ->
                navController.navigate(NavRoute.LearnMode.withArgs(id.toString(), showDetails.toString()))
            },
            navigateToExercise = {navController.navigate(NavRoute.ExerciseMode.path)},
            navigateToHome = {navController.navigate(NavRoute.Home.path)}
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
        route = NavRoute.ExerciseMode.path,
    ){
        ExerciseScreen(
            popBackStack = { navController.popBackStack() },
            navigateToResult = { result ->
                navController.navigate(NavRoute.Result.withArgs(result.toString()))
            }
        )
    }
}



private fun addResultScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
){
    navGraphBuilder.composable(
        route = NavRoute.Result.withArgsFormat(NavRoute.Result.score),
        arguments = listOf(
            navArgument(NavRoute.Result.score) {
                type = NavType.IntType
            },
        )){
            navBackStackEntry -> val args = navBackStackEntry.arguments
        ResultScreen(
            score = args?.getInt(NavRoute.Result.score)!!,
            navigateToMode = {
                navController.navigate(NavRoute.Mode.path)
            },
            popBackStack = { navController.popBackStack() }
        )
    }
}



/*
private fun addScheduleScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
){
    navGraphBuilder.composable(route = NavRoute.Schedule.path) {

        ScheduleScreen(
            popBackStack = { navController.popBackStack() }
            //popBackStack = { navController.popBackStack() }
            //popUpToLogin= { popUpToLogin(navController) },
        )
    }

}*/

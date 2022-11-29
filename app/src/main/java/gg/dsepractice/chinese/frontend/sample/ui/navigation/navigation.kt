package gg.dsepractice.chinese.frontend.sample.ui.theme

sealed class NavRoute(val path: String) {


    object Login : NavRoute("login"){
    }

    object Home: NavRoute("home"){
    }

    object Mode: NavRoute("mode"){

    }

    object LearnMode: NavRoute("learn") {
        val id = "id"
        val showDetails = "showDetails"
    }
    object ExerciseMode: NavRoute("exercise") {
        //val id = "id"
    }

    object Result: NavRoute("result"){
        val score = "score"

    }

    //object Schedule: NavRoute("schedule")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}


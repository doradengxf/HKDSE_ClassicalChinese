package gg.dsepractice.chinese.frontend.sample

import android.view.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.Module
import org.w3c.dom.Text

@Composable
fun HomeScreen(
    navigateToLearn: (Int, Boolean) -> Unit,
    navigateToExercise: (Int) -> Unit,
    //popBackStack: () -> Unit,
    //popUpToLogin: () -> Unit,
) {

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Home Screen", fontSize = 40.sp)

        Button(
            onClick = { navigateToLearn(7, true) }
        ){
            Text("Learn");
        }

        Button(
            onClick = { navigateToExercise(1) },
        ){
            Text("Exercise") ;
        }


    }

}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeScreen(
                navigateToLearn = { _,_ -> },
                navigateToExercise = {},
                //popUpToLogin = {}
            )
        }
    }

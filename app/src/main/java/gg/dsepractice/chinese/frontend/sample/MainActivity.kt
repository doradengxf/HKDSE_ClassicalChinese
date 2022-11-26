package gg.dsepractice.chinese.frontend.sample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier
import javax.inject.Inject
import gg.dsepractice.chinese.frontend.sample.ui.navigation.NavGraph


@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    NavGraph(navController)
}

//@AndroidEntryPoint
/*@Module
@InstallIn(SingletonComponent::class)
class MainActivity @Inject() constructor() : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel(this)
        lifecycle.addObserver(mainViewModel)
        setContent {
            MainScreen()
            }
        }
}*/

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun TextCell(text: String, modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier, fontSize: Int = 25 ) {

    val cellModifier = androidx.compose.ui.Modifier
        .padding(8.dp)
        .border(width = 2.dp, color = Color.Black)

    Text(
        text = text, cellModifier.then(modifier),
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

/*
@Preview
@Composable
fun MainActivity() {
    HomeScreen()
}*/

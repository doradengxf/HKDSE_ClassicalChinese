package gg.dsepractice.chinese.frontend.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import gg.dsepractice.chinese.frontend.sample.ui.navigation.NavGraph


@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    NavGraph(navController)
}

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

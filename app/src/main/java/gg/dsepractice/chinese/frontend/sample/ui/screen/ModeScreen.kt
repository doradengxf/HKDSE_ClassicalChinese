package gg.dsepractice.chinese.frontend.sample.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gg.dsepractice.chinese.frontend.sample.LearnScreen
import gg.dsepractice.chinese.frontend.sample.R
import gg.dsepractice.chinese.frontend.sample.TextCell


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModeScreen(
    popBackStack: () -> Unit,
    navigateToLearn: (Int, Boolean) -> Unit,
    navigateToExercise: () -> Unit,
    navigateToHome: () -> Unit,
    //popUpToLogin: () -> Unit
) {
    Scaffold(
        //Top Bar
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navigateToHome()}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia arriba")
                    }
                },
                title = { Text(text = "<<論仁、論孝、論君子>>",textAlign = TextAlign.Center,) },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.navi),
                            contentDescription = "Leer después"
                        )
                    }
                }
            )

        },
        //bottom Bar
        bottomBar = {
            val selectedIndex = remember { mutableStateOf(0) }
            BottomNavigation(elevation = 10.dp) {

                BottomNavigationItem(icon = {
                    Icon(painter = painterResource(id = R.drawable.study),
                        contentDescription = "Leer después"
                    )
                },
                    label = { Text(text = "學習") },
                    selected = (selectedIndex.value == 0),
                    onClick = {
                        selectedIndex.value = 0
                    })

                BottomNavigationItem(icon = {
                    Icon(painter = painterResource(id = R.drawable.data),
                        contentDescription = "Leer después"
                    )
                },
                    label = { Text(text = "數據") },
                    selected = (selectedIndex.value == 1),
                    onClick = {
                        selectedIndex.value = 1
                    })

                BottomNavigationItem(icon = {
                    Icon(painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Leer después"
                    )
                },
                    label = { Text(text = "日程") },
                    selected = (selectedIndex.value == 2),
                    onClick = {
                        selectedIndex.value = 2
                    })
            }
        }) {
        //Main Body
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            {
                Column(modifier = Modifier
                    .padding(all = 5.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(
                        onClick = { navigateToLearn(7, true) }
                    ) {
                        Image(painter = painterResource(id = R.drawable.q4), contentDescription = null)
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("學習模式")
                        }}

                    Card(
                        elevation = 4.dp,
                        onClick = {navigateToExercise()}
                        ) {
                            Image(painter = painterResource(id = R.drawable.q2), contentDescription = null)
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("練習模式")
                            }
                    }


                    Card(
                        elevation = 4.dp,
                    ) {
                        Image(painter = painterResource(id = R.drawable.img_2), contentDescription = null)
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("測試模式")
                        }
                    }



            }}
}}



@Preview(showBackground = true)
@Composable
private fun ModePreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        ModeScreen(
            popBackStack = {},
            navigateToLearn = { _,_ -> },
            navigateToExercise = {},
            navigateToHome = {}
            //popUpToLogin = {}
        )
    }

}
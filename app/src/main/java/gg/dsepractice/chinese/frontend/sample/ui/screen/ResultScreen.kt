package gg.dsepractice.chinese.frontend.sample.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gg.dsepractice.chinese.frontend.sample.R
import gg.dsepractice.chinese.frontend.sample.TextCell

@Composable

fun ResultScreen(
    score: Int,
    navigateToMode: () -> Unit,
    popBackStack: () -> Unit,
){
    Scaffold(
        //Top Bar
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
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
        },

        content= {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(80.dp)
                    .fillMaxSize()) {
                Text(text = "You Got $score scores!",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,)
                Card() {
                    Image(painter = painterResource(id = R.drawable.results), contentDescription = null )

                }
            }
        }
    )
}

@Preview
@Composable
fun ResultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        ResultScreen(
            score = 4,
            navigateToMode = {},
            popBackStack = {},
            //popUpToLogin = {}
        )
    }
}

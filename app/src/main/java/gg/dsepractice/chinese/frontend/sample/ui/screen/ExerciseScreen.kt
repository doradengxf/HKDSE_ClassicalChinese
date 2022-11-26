package gg.dsepractice.chinese.frontend.sample

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ExerciseScreen(
    id: Int,
    popBackStack: () -> Unit,
    //popUpToLogin: () -> Unit
) {

    Scaffold(
        //Top Bar
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = popBackStack ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia arriba")
                    }
                },
                title = { Text(text = "<<師說>>",textAlign = TextAlign.Center) },
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
        //Main Body
        content = {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(start = 3.dp)
                    .fillMaxSize()
            )
            {
                Column {
                    TextCell(
                        "孔子認為「仁」的最高層次是什麼？",
                        Modifier
                            .size(400.dp, 200.dp)
                            .padding(5.dp)
                    )

                    TextCell(
                        "1",
                        Modifier
                            .size(400.dp, 100.dp)
                            .padding(5.dp)
                    )
                    TextCell(
                        "2",
                        Modifier
                            .size(400.dp, 100.dp)
                            .padding(5.dp)
                    )
                    TextCell(
                        "3",
                        Modifier
                            .size(400.dp, 100.dp)
                            .padding(5.dp)
                    )
                    TextCell(
                        "4",
                        Modifier
                            .size(400.dp, 100.dp)
                            .padding(5.dp)
                    )
                }

            }
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
                        label = { Text(text = "練習") },
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
            }
    )

}




@Preview
@Composable
fun ExercisePreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        ExerciseScreen(
            id = 7,
            popBackStack = {},
            //popUpToLogin = {}
        )
    }
}


package gg.dsepractice.chinese.frontend.sample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


@Composable
fun LearnScreen(
    id: Int,
    showDetails: Boolean,
    popBackStack: () -> Unit,
    //popUpToLogin: () -> Unit
) {

    Scaffold(
        //Top Bar
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia arriba")
                    }
                },
                title = { Text(text = "<<師說>>",textAlign = TextAlign.Center,) },
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
                    .padding(start = 8.dp)
                    .fillMaxSize()
            )
            {
                Column() {
                    TextCell( "古之學者必有師，師者，所以傳道授業解惑也。",
                        Modifier
                            .size(400.dp, 280.dp)
                            .padding(8.dp))

                    Row{

                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(start = 8.dp, end = 32.dp)) {
                            Text(text = "翻譯")
                        }
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(start = 8.dp, end = 32.dp)) {
                            Text(text = "筆記")
                        }
                    }

                    TextCell( "古代求學的人一定有老師。",
                        Modifier
                            .size(400.dp, 300.dp)
                            .padding(8.dp))
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
        }
    )
    }



@Preview(showBackground = true)
@Composable
private fun LearnPreview() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            LearnScreen(
                id = 7,
                showDetails = true,
                popBackStack = {},
                //popUpToLogin = {}
            )
        }

}







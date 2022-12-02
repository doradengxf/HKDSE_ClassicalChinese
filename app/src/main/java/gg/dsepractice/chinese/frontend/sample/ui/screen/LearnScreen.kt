package gg.dsepractice.chinese.frontend.sample


import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


val text = listOf(
    Text("子曰：「不仁者，不可以久處約，不可以長處樂。仁者安仁，知者利仁。」（《里仁》第四）","孔子說：「沒有仁德的人，不可以長期處於窮困的境況中，不可以長期處於安樂的境況中。有仁德的人安於實踐仁，有智慧的人藉着實踐仁德而得到利益。」"),
    Text("子曰：「富與貴，是人之所欲也；不以其道得之，不處也。貧與賤，是人之所惡也；不以其道得之，不去也。君子去仁，惡乎成名？君子無終食之間違仁，造次必於是，顛沛必於是。」（《里仁》第四）","孔子說：「財富和顯貴，是人人都想得到的；不以正當的方法得到它們，不可以接受；貧窮和卑賤，是人人都厭惡的，不以正當的方法擺脫它們，不可以離去。君子拋棄了仁德，怎樣成就他的名聲？即使短至一頓飯的時間，君子也不會違背仁德，倉卒緊迫的時候必定如此，顛沛流離的時候也必如此。」") ,
    Text("顏淵問仁。子曰：「克己復禮為仁。一日克己復禮，天下歸仁焉。為仁由己，而由人乎哉？」\n" +
            "顏淵曰：「請問其目。」子曰：「非禮勿視，非禮勿聽，非禮勿言，非禮勿動。」\n" +
            "顏淵曰：「回雖不敏，請事斯語矣。」（《顏淵》第十二）","顏淵問甚麼是仁德。孔子說：「克制自己的私慾，使言行舉止合乎禮節，就是仁德。一旦能做到這一點，天下的人都會歸服你的仁德。實踐仁德全憑自己，難道是憑藉別人嗎？」\n" +
            "顏淵說：「請問仁德包括甚麼條目？」\n" +
            "孔子說：「不看不合乎禮節的東西，不聽不合乎禮節的說話，不說不合乎禮節的話語，不做不合乎禮節的事情。」\n" +
            "顏淵說：「我雖然不聰敏，請讓我照着這些話去做。」")
)


@Composable
fun LearnScreen(
    id: Int,
    showDetails: Boolean,
    popBackStack: () -> Unit,
    //popUpToLogin: () -> Unit
) {
    val mCounter = remember { mutableStateOf(0)}
    var isTranslated = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        //Top Bar
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia arriba")
                    }
                },
                title = { Text(text = "<<論仁、論孝、論君子>>",textAlign = TextAlign.Center) },
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
                    TextCell( text[mCounter.value].origin,
                        Modifier
                            .size(400.dp, 280.dp)
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState()))

                    Row{

                        Button(onClick = {isTranslated.value = true},
                            modifier = Modifier
                                .padding(start = 8.dp, end = 32.dp)) {
                            Text(text = "翻譯")
                        }
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(start = 8.dp, end = 32.dp)) {
                            Text(text = "筆記")
                        }
                        Button(modifier = Modifier
                                .padding(start = 8.dp, end = 32.dp),
                            onClick = { mCounter.value++;
                                isTranslated.value = false}) {
                            Text(text = "->")
                        }
                    }

                    if (isTranslated.value) {
                        TextCell(text[mCounter.value].translate,
                            Modifier
                                .size(400.dp, 280.dp)
                                .padding(8.dp)
                                .verticalScroll(rememberScrollState()))
                    }else{
                        TextCell("",
                            Modifier
                                .size(400.dp, 280.dp))
                    }


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
                        selectedIndex.value = 2;
                        context.startActivity(Intent(context, ScheduleActivity::class.java))
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







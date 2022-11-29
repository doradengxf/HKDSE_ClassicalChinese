package gg.dsepractice.chinese.frontend.sample

import android.service.autofill.OnClickAction
import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ExerciseScreen(
    //id: Int,
    popBackStack: () -> Unit,
    navigateToResult: (Int) -> Unit
    //popUpToLogin: () -> Unit
) {
    val mCounter = remember { mutableStateOf(0)}
    var score = 0

    //data class Question(val question:String, var a: String, val b:String,var c:String, var d:String, var answer: String )
    val questions = listOf(
        QuestionModel("1. 不仁者，不可以久處約，   （約）","利用","窮困","安樂","智","窮困"),
        QuestionModel("2. 不可以長處樂。   （樂）","利用","智","安樂","接受","安樂"),
        QuestionModel("3. 仁者安仁，知者利仁。    （智）","智","利用","接受","厭惡","智"),
        QuestionModel("4. 仁者安仁，知者利仁。    （利）","離棄","厭惡","利用","接受","利用"),
        QuestionModel("5. 不以其道得之，不處也。   （處）","厭惡","離棄","接受","如何","接受"),
    )


    Scaffold(
        //Top Bar
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = popBackStack ) {
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
                    .padding(start = 7.dp)
            )
            {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)) {
                    TextCell(
                        questions[mCounter.value].question.toString(),
                        Modifier
                            .size(400.dp, 150.dp)
                            .padding(5.dp)
                    )

                    Button(
                        onClick = {
                                if (mCounter.value<4){
                                    mCounter.value++
                                    if(questions[mCounter.value].option1.toString()==questions[mCounter.value].answer.toString()){
                                        score++
                                }else{navigateToResult(score)}}
                        Modifier
                            .size(400.dp, 125.dp)
                            .padding(5.dp)
                        }
                    ){
                        Text(text = questions[mCounter.value].option1.toString())
                    }

                    Button(
                        onClick = {
                            if (mCounter.value<4){
                                mCounter.value++
                                if(questions[mCounter.value].option2.toString()==questions[mCounter.value].answer.toString()){
                                    score++
                            }}else{navigateToResult(score)};
                        Modifier
                            .size(400.dp, 125.dp)
                            .padding(5.dp)}){
                        Text(text = questions[mCounter.value].option2.toString())
                    }

                    Button(
                        onClick = {
                                if (mCounter.value<4){
                                    mCounter.value++
                                    if(questions[mCounter.value].option3.toString()==questions[mCounter.value].answer.toString()){
                                        score++
                                }}else{navigateToResult(score)};
                            Modifier
                                .size(400.dp, 125.dp)
                                .padding(5.dp)}){
                        Text(text = questions[mCounter.value].option3.toString())
                    }

                    Button(
                        onClick = {
                                if (mCounter.value<4){
                                    mCounter.value++
                                    if(questions[mCounter.value].option4.toString()==questions[mCounter.value].answer.toString()){
                                        score++
                                    }
                                }else{navigateToResult(score)};
                            Modifier
                                .size(400.dp, 125.dp)
                                .padding(5.dp)}){
                        Text(text = questions[mCounter.value].option4.toString())
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
            //id = 7,
            popBackStack = {},
            navigateToResult={},
            //popUpToLogin = {}
        )
    }
}







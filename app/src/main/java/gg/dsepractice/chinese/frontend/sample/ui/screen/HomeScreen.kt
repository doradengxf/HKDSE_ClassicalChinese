package gg.dsepractice.chinese.frontend.sample


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




data class Article(val name:String, var painter: Painter, val index:Int)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navigateToMode: () -> Unit,
    navigateToLearn: (Int, Boolean) -> Unit,
    navigateToExercise: () -> Unit,
    //popBackStack: () -> Unit,
    //popUpToLogin: () -> Unit,
) {
    val articles = listOf(
        Article("論語〈論仁、論孝、論君子>", painterResource(R.drawable.a1), 0),
        Article("孟子《魚我所欲也》", painterResource(R.drawable.a2), 1),
        Article("李白《月下獨酌》(其一)", painterResource(R.drawable.a3), 2),
        Article("司馬遷《廉頗藺相如列傳》", painterResource(R.drawable.a4), 3),
        Article("王維《山居秋暝》", painterResource(R.drawable.a5), 4),
        Article("柳宗元《始得西山宴遊記》", painterResource(R.drawable.a6), 5),
        Article("杜甫《登樓》", painterResource(R.drawable.a7), 6),
        Article("李清照《聲聲慢．秋情》", painterResource(R.drawable.a8), 7),
        Article("范仲淹《岳陽樓記》", painterResource(R.drawable.a9), 8),
        Article("范仲淹《岳陽樓記》", painterResource(R.drawable.a10), 9),
        Article("荀子《勸學》（節錄）", painterResource(R.drawable.a11), 10),
        Article("莊子《逍遙遊》", painterResource(R.drawable.a12), 11),
        Article("蘇洵《六國論》", painterResource(R.drawable.a13), 12),
        Article("蘇軾《念奴嬌．赤壁懷古》", painterResource(R.drawable.a1), 13),
        Article("諸葛亮《出師表》", painterResource(R.drawable.a1), 14),
        Article("辛棄疾《青玉案．元夕》", painterResource(R.drawable.a1), 15),
        Article("韓愈《師說》", painterResource(R.drawable.a1), 16)
    )
    Scaffold(
        //Top Bar
        topBar ={
            TopAppBar(
                title = { Text(text = "主頁",textAlign = TextAlign.Center,) },
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
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                for (i in 0..15) {
                    Card(
                        onClick = { navigateToMode() },
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .height(150.dp)
                            .width(350.dp)
                            //.fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                    )
                    {
                        Image(
                            painter = articles[i].painter,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                        )
                        Column(modifier = Modifier.width(5.dp)) {
                            Text(
                                articles[i].name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                modifier = Modifier.padding(10.dp)
                            );
                        };
                    }
                }


/*        Card(
            onClick = { navigateToLearn(7, true) }
        ) {
            Image(painter = painterResource(id = R.drawable.q4), contentDescription = null)
            Column(modifier = Modifier.padding(10.dp)) {
                Text("學習模式")
            }}

        }*/
            }
        })
}

/*
@Composable
fun Clickable(
    navigateToExercise: (Int) -> Unit
){
    Button(onClick = { navigateToExercise }) {

    }
}
*/



@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeScreen(
                navigateToMode ={},
                navigateToLearn = { _,_ -> },
                navigateToExercise = {},
                //popUpToLogin = {}
            )
        }
    }

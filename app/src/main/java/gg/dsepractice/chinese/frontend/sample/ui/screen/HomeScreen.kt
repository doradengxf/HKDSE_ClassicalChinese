package gg.dsepractice.chinese.frontend.sample

import android.view.Surface
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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



data class Article(val name:String, var painter: Painter, val index:Int)


@Composable
fun HomeScreen(
    navigateToMode: () -> Unit,
    navigateToLearn: (Int, Boolean) -> Unit,
    navigateToExercise: (Int) -> Unit,
    //popBackStack: () -> Unit,
    //popUpToLogin: () -> Unit,
) {
    val articles = listOf(
        Article("論語〈論仁、論孝、論君子>", painterResource(R.drawable.a1),0),
        Article("孟子《魚我所欲也》",painterResource(R.drawable.a2),1),
        Article("李白《月下獨酌》(其一)",painterResource(R.drawable.a3),2),
        Article("司馬遷《廉頗藺相如列傳》",painterResource(R.drawable.a4),3),
        Article("王維《山居秋暝》",painterResource(R.drawable.a5),4),
        Article("柳宗元《始得西山宴遊記》",painterResource(R.drawable.a6),5),
        Article("杜甫《登樓》",painterResource(R.drawable.a7),6),
        Article("李清照《聲聲慢．秋情》",painterResource(R.drawable.a8),7),
        Article("范仲淹《岳陽樓記》",painterResource(R.drawable.a9),8),
        Article("范仲淹《岳陽樓記》",painterResource(R.drawable.a10),9),
        Article("荀子《勸學》（節錄）",painterResource(R.drawable.a11),10),
        Article("莊子《逍遙遊》",painterResource(R.drawable.a12),11),
        Article("蘇洵《六國論》",painterResource(R.drawable.a13),12),
        Article("蘇軾《念奴嬌．赤壁懷古》",painterResource(R.drawable.a1),13),
        Article( "諸葛亮《出師表》",painterResource(R.drawable.a1),14),
        Article("辛棄疾《青玉案．元夕》",painterResource(R.drawable.a1),15),
        Article("韓愈《師說》",painterResource(R.drawable.a1),16)
    )


    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        for (i in 0..15) {
            Button(modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
                onClick = { navigateToMode()})
            {
                Spacer(modifier = Modifier.width(5.dp));
                Text(articles[i].name,fontSize = 25.sp, modifier = Modifier.padding(10.dp));
                Image(painter = articles[i].painter,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),);
                    }
            }
        }
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

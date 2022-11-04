package gg.dsepractice.chinese.frontend.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import gg.dsepractice.chinese.frontend.sample.ui.theme.HKDSEPracticeAndroidSampleAppTheme
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@Module
@InstallIn(SingletonComponent::class)
class MainActivity @Inject() constructor() : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel(this)
        lifecycle.addObserver(mainViewModel)
        setContent {
            HKDSEPracticeAndroidSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(mainViewModel = mainViewModel)
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    HKDSEPracticeAndroidSampleAppTheme {
//        MainScreen()
//    }
//}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val scope = rememberCoroutineScope()

    Column {
        Button(onClick = {
            mainViewModel.startAuthUISignInFlow()
        }) {
            Text(text = "Sign In")
        }
        Button({
            scope.launch {
                mainViewModel.signOut()
            }
        }) {
            Text(text = "Sign Out")
        }
        Text(text = mainViewModel.currentUser?.email ?: "not logged in")
        Text(text = mainViewModel.errMessage ?: "no error")
        Button({
            scope.launch {
                mainViewModel.fetchArticleList()
            }
        }) {
            Text(text = "Call listArticles")
        }
        Text(text = mainViewModel.articleList ?: "no articleList")
        Button({
            scope.launch {
                mainViewModel.fetchActiveGameSessions()
            }
        }) {
            Text(text = "Call listActiveGameSessions")
        }
        Text(text = mainViewModel.activeGameSessions ?: "no activeGameSessions")
    }
}
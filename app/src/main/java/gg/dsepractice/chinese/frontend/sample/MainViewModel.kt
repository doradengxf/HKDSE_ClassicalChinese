package gg.dsepractice.chinese.frontend.sample

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.net.Uri
import android.telecom.Call
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import gg.dsepractice.chinese.v1alpha1.*
import io.grpc.CallCredentials
import io.grpc.CallOptions
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.tasks.await


class MainViewModel constructor(
    @SuppressLint("StaticFieldLeak") private val componentActivity: ComponentActivity
) : AndroidViewModel(componentActivity.application), DefaultLifecycleObserver {
    private lateinit var auth: FirebaseAuth
    private lateinit var channel: ManagedChannel
    private lateinit var coreService: CoreServiceGrpcKt.CoreServiceCoroutineStub

    var currentUser by mutableStateOf<FirebaseUser?>(null)
        private set

    var errMessage by mutableStateOf<String?>(null)
        private set

    var articleList by mutableStateOf<String?>(null)

    var activeGameSessions by mutableStateOf<String?>(null)

    private val signInLauncher = componentActivity.registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInActivityResult(res)
    }

    private fun resetError() {
        errMessage = null
    }

    override fun onCreate(owner: LifecycleOwner) {
        auth = FirebaseAuth.getInstance()

        channel = let {
            val uri = Uri.parse(componentActivity.getString(R.string.core_service_url))
            val builder = ManagedChannelBuilder.forAddress(uri.host, uri.port)
            if (uri.scheme == "https") {
                builder.useTransportSecurity()
            } else {
                builder.usePlaintext()
            }
            builder.executor(Dispatchers.IO.asExecutor()).build()
        }

        coreService = CoreServiceGrpcKt.CoreServiceCoroutineStub(
            channel,
            callOptions = CallOptions.DEFAULT
        )

    }

    override fun onStart(owner: LifecycleOwner) {
        onAuthStateChanged()
    }

    private fun onAuthStateChanged() {
        currentUser = auth.currentUser
        currentUser?.getIdToken(true)?.addOnCompleteListener {
            val token = it.result?.token
            coreService = if (token == null) {
                CoreServiceGrpcKt.CoreServiceCoroutineStub(
                    channel,
                    callOptions = CallOptions.DEFAULT
                )
            } else {
                CoreServiceGrpcKt.CoreServiceCoroutineStub(
                    channel,
                    callOptions = CallOptions.DEFAULT.withCallCredentials(
                        FirebaseAuthCredential(
                            token
                        )
                    )
                )
            }

        }
    }

    fun startAuthUISignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signInIntent =
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInActivityResult(res: FirebaseAuthUIAuthenticationResult?) {
        val response = res?.idpResponse
        errMessage = if (res?.resultCode == RESULT_OK) {
            onAuthStateChanged()
            null
        } else {
            if (res == null || res.idpResponse == null) {
                "Sign in flow cancelled."
            } else {
                val err = response?.error
                "Sign in failed: ${err?.message} (${err?.errorCode})"
            }
        }
    }

    suspend fun signOut() {
        try {
            AuthUI.getInstance().signOut(componentActivity.application).await()
            onAuthStateChanged()
            resetError()
        } catch (e: Throwable) {
            errMessage = e.message
        }
    }

    suspend fun fetchArticleList() {
        try {
            val articles = coreService.listArticles(
                listArticlesRequest {
                    paginationOption = paginationOption {
                        limit = 5
                    }
                }
            )
            articleList = articles.articlesList.joinToString(" ") {
                it.name ?: ""
            }
            resetError()
        } catch (e: NoClassDefFoundError) {
            throw e
        } catch (e: Throwable) {
            errMessage = e.message
        }
    }

    suspend fun fetchActiveGameSessions() {
        try {
            val sessions = coreService.listActiveGameSessions(
                listActiveGameSessionsRequest { }
            )
            activeGameSessions = sessions.gameSessionsList.joinToString(" ") {
                "${it.id} ${it.articleId}"
            }
            resetError()
        } catch (e: NoClassDefFoundError) {
            throw e
        } catch (e: Throwable) {
            errMessage = e.message
        }
    }
}
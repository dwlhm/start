package com.dwlhm.start

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dwlhm.home.ui.HomeScreen
import com.dwlhm.nav.AppNavHost
import com.dwlhm.nav.Routes
import com.dwlhm.onboarding.ui.OnboardingScreen
import com.dwlhm.start.ui.theme.StartTheme
import com.dwlhm.storage.prefs.onboarding.OnboardingPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var onboardingPrefs: OnboardingPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            StartTheme {
                MainScreen(onboardingPrefs)
            }
        }
    }
}

@Composable
fun MainScreen(onboardingPrefs: OnboardingPrefs) {
    Surface(color = MaterialTheme.colorScheme.background) {
        val scope = rememberCoroutineScope()
        val startState = remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            startState.value = if (onboardingPrefs.hasOnBoarded()) Routes.HOME else Routes.ONBOARDING
        }

        if (startState.value != null) {
            AppNavHost(
                startDestination = startState.value!!,
                registerAll = { registrar, route ->
                    registrar.register(Routes.ONBOARDING) {
                        OnboardingScreen(onFinish = {
                            scope.launch {
                                onboardingPrefs.markOnBoarded()
                                route.navigate(
                                    route = Routes.HOME,
                                    popUpFrom = Routes.ONBOARDING,
                                    inclusive = true,
                                )
                            }
                        })
                    }
                    registrar.register(Routes.HOME) {
                        HomeScreen()
                    }
                }
            )
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StartTheme {
        Greeting("Android")
    }
}
package com.example.composenewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.composenewsapp.base.MyAppNavHost
import com.example.composenewsapp.network.ConnectionState
import com.example.composenewsapp.network.connectivityState
import com.example.composenewsapp.screens.*
import com.example.composenewsapp.ui.theme.ComposeNewsAppTheme
import com.example.composenewsapp.viewModels.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("OPT_IN_IS_NOT_ENABLED")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feedViewModel: FeedViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsAppTheme {

                val connectionState by connectivityState()
                val isConnected = connectionState === ConnectionState.Available
                val hasNet = remember {
                    mutableStateOf(!isConnected)
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyAppNavHost(feedViewModel = feedViewModel,
                        navController = rememberNavController(),
                        context = this)
                    NoInternetScreen(openFullDialogCustom = hasNet)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeNewsAppTheme {
        HomeScreenListView(onNavigateToFeed = {})
    }
}
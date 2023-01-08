package com.example.composenewsapp.base

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composenewsapp.network.ConnectionState
import com.example.composenewsapp.network.currentConnectivityState
import com.example.composenewsapp.screens.FeedDetailUI
import com.example.composenewsapp.screens.FeedsScreen
import com.example.composenewsapp.screens.HomeScreenListView
import com.example.composenewsapp.utills.ScreenRoutes
import com.example.composenewsapp.viewModels.FeedViewModel

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home",
    feedViewModel: FeedViewModel,
    context : Context
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ScreenRoutes.Home) {
            HomeScreenListView(
                onNavigateToFeed = { endPoint ->
                    if (context.currentConnectivityState == ConnectionState.Available) {
                        feedViewModel.getFeeds(endPoint)
                    } else {
                        feedViewModel.getDataInLocal(endPoint)
                    }

                    navController.navigate("feed/$endPoint") },
            )
        }
        composable(
            "${ScreenRoutes.Feed}/{endPoint}",
            arguments = listOf(navArgument("endPoint") { type = NavType.StringType })
        ) { backStackEntry ->
            FeedsScreen(
                feedViewModel,
                backStackEntry.arguments?.getString("endPoint"),
                navController
            ) {
                navController.navigate("feedDetail")
            }
        }

        composable(
            ScreenRoutes.FeedDetail,
        ) {
            FeedDetailUI(feedViewModel , navController)
        }
    }
}
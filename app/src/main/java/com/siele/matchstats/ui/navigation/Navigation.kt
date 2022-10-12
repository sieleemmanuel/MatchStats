package com.siele.matchstats.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.siele.matchstats.ui.screens.DashboardContent
import com.siele.matchstats.ui.screens.LeagueInfo
import com.siele.matchstats.util.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.DashboardScreen.route
    ){
        composable(
            route = Screen.DashboardScreen.route
        ){
            DashboardContent(navController)
        }

        composable(
            route = Screen.LeagueInfoScreen.route + "/{league_name}",
            arguments = listOf(
                navArgument("league_name"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            LeagueInfo(
                navController = navController,
                leagueName = entry.arguments?.getString("league_name")
            )
        }
    }

}

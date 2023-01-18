package com.siele.matchstats.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.siele.matchstats.ui.screens.DashboardContent
import com.siele.matchstats.ui.screens.LeagueInfo
import com.siele.matchstats.util.Screen

@RequiresApi(Build.VERSION_CODES.O)
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
            route = Screen.LeagueInfoScreen.route + "/{league_name}/{league_id}/{league_type}",
            arguments = listOf(
                navArgument("league_name"){ type = NavType.StringType },
                navArgument("league_id"){ type = NavType.StringType },
                navArgument("league_type"){ type = NavType.StringType }
            )
        ){ entry ->
            LeagueInfo(
                navController = navController,
                leagueName = entry.arguments?.getString("league_name"),
                leagueId = entry.arguments?.getString("league_id")!!,
                type = entry.arguments?.getString("league_type")!!
            )
        }
    }

}

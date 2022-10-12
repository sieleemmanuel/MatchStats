package com.siele.matchstats.util

sealed class Screen(var route:String){
    object DashboardScreen:Screen("dashboard")
    object LeagueInfoScreen:Screen("league_info")
}
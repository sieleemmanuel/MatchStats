package com.siele.matchstats.util

import com.siele.matchstats.data.model.leagues.League

object Constants {
    val BASE_URL = "https://v3.football.api-sports.io/"
    val LOCAL_BASE_URL = "file:///assets/"


    val leagues = mutableListOf(
        League(1,"","Premier League",""),
        League(2,"","Bundesliga",""),
        League(3,"","Ligue 1",""),
        League(4,"","Laliga ",""),
        League(5,"","UEFA Champions League",""),
        League(6,"","UEFA Europa League",""),
        League(7,"","Conference League",""),
        League(8,"","Premier Liga",""),
        League(9,"","Evredivisie",""),
        League(10,"","Serie A",""),
        League(11,"","Liga Portugal",""),
        League(12,"","EFL Championship",""),
        League(13,"","Scottis Premiership",""),
        League(14,"","Russia Premier League",""),
        League(15,"","Danish Superliga",""),
        League(16,"","Swiss Super League",""),
        League(17,"","Premier League",""),
    )
    val seasons = listOf("2010-2011","2011-2012", "2012-2013", "2013-2014", "2014-2015",
        "2015-2016", "2016-2017", "2017-2018", "2019-2020", "2020-2021", "2021-2022","2022-2023")
    val eplTeams = listOf("Man city", "Man united", "Liverpool", "Arsenal", "Chelsea", "Southampton",
        "Everton", "Nottingham Forest", "Leeds", "Fulham", "Toteham Hotspurs", "Crystal Palace", "Aston Villa",
        "West Ham", "Wolves", "Leicester", "Bournemouth", "Brentford", "Brighton", "Newcastle"
    )
}
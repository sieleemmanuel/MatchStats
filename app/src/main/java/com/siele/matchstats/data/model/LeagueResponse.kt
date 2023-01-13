package com.siele.matchstats.data.model

data class LeagueResponse(
    val country: Country,
    val league: League,
    val seasons: List<Season>
)
package com.siele.matchstats.data.model

data class Response(
    val country: Country,
    val league: League,
    val seasons: List<Season>
)
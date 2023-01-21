package com.siele.matchstats.data.model.teams

data class Venue(
    val address: String,
    val capacity: Int,
    val city: String,
    val id: Int,
    val image: String,
    val name: String,
    val surface: String
)
package com.siele.matchstats.data.model.standings

data class All(
    val draw: Int,
    val goals: Goals,
    val lose: Int,
    val played: Int,
    val win: Int
)
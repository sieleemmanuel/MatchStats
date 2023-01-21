package com.siele.matchstats.data.model.stats

data class PlayerResponse(
    val player: Player,
    val statistics: List<Statistic>
)
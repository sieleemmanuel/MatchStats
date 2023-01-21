package com.siele.matchstats.data.model.stats.assists

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "league_assists")
data class LeagueTopAssists(
    @PrimaryKey(autoGenerate = false)
    val leagueId: String,
    val seasonAssists: List<SeasonTopAssists>
)

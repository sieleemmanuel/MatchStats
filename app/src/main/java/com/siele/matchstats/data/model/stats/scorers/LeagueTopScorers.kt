package com.siele.matchstats.data.model.stats.scorers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "league_scorers")
data class LeagueTopScorers(
    @PrimaryKey(autoGenerate = false)
    val leagueId: String,
    val seasonScorers: List<SeasonTopScorers>
)

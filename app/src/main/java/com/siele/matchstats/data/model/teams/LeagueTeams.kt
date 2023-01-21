package com.siele.matchstats.data.model.teams

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "league_teams")
data class LeagueTeams(
    @PrimaryKey(autoGenerate = false)
    val leagueId:String = "",
    val seasonTeams: List<SeasonTeams>
)

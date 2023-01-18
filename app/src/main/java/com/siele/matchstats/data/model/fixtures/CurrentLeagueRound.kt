package com.siele.matchstats.data.model.fixtures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "league_current_round_table")
data class CurrentLeagueRound(
   @PrimaryKey(autoGenerate = false)
   val leagueId:String = "",
   val currentRound:String = "",
)
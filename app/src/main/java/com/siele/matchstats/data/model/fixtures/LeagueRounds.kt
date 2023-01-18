package com.siele.matchstats.data.model.fixtures

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.siele.matchstats.data.database.ListsConverter

@Entity(tableName = "league_rounds_table")
@TypeConverters(ListsConverter::class)
data class LeagueRounds(
   @PrimaryKey(autoGenerate = false)
   val leagueId:String = "",
   val rounds:List<String>,
)
package com.siele.matchstats.data.model.standings

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.siele.matchstats.data.database.ListsConverter

@Entity(tableName = "league_standing_table")
@TypeConverters(ListsConverter::class)
data class LeagueStanding(
    @PrimaryKey(autoGenerate = false)
    val leagueId:String = "",
    val standings:List<List<Standing>> = emptyList()

)

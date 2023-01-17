package com.siele.matchstats.data.model.leagues

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.siele.matchstats.data.database.ListsConverter

@Entity(tableName = "leagues_table")
@TypeConverters(ListsConverter::class)
data class LeagueResponse(
    val country: Country,
    val league: League,
    val seasons: List<Season>,
    @PrimaryKey(autoGenerate = true) val id:Int = -1
){

}
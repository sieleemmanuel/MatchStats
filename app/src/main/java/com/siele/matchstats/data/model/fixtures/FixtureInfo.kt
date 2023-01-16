package com.siele.matchstats.data.model.fixtures

import androidx.room.Entity
import androidx.room.TypeConverters
import com.siele.matchstats.data.database.ListsConverter

@Entity(tableName = "fixtures_table")
@TypeConverters(ListsConverter::class)
data class FixtureInfo(
    val fixture: Fixture,
    val goals: Goals,
    val league: FixtureLeague,
    val score: Score,
    val teams: Teams
)
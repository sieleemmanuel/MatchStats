package com.siele.matchstats.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.fixtures.LeagueFixtures
import com.siele.matchstats.data.model.fixtures.LeagueRound
import com.siele.matchstats.data.model.leagues.LeagueResponse

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagues(leagues:List<LeagueResponse>)

    @Query("SELECT * FROM leagues_table")
    suspend fun getLeagues():List<LeagueResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixtures(fixtures:LeagueFixtures)

    @Query("SELECT * FROM league_fixtures WHERE leagueId =:leagueId")
    suspend fun getFixtures(leagueId: String):LeagueFixtures

    @Query("SELECT EXISTS(SELECT * FROM league_fixtures WHERE leagueId=:leagueId)")
    suspend fun fixtureExist(leagueId:String):Boolean

    @Query("DELETE FROM leagues_table")
    suspend fun deleteLeagues()

    @Query("DELETE FROM league_fixtures")
    suspend fun deleteFixtures()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentRound(leagueRound: LeagueRound)

    @Query("SELECT * FROM league_rounds_table WHERE leagueId=:leagueId")
    suspend fun getCurrentRound(leagueId: String):LeagueRound

}
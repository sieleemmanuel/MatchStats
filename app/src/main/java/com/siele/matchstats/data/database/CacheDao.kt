package com.siele.matchstats.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siele.matchstats.data.model.stats.LeagueTopScorers
import com.siele.matchstats.data.model.fixtures.LeagueFixtures
import com.siele.matchstats.data.model.fixtures.CurrentLeagueRound
import com.siele.matchstats.data.model.fixtures.LeagueRounds
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.data.model.standings.LeagueStanding

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
    suspend fun insertCurrentRound(currentLeagueRound: CurrentLeagueRound)

    @Query("SELECT * FROM league_current_round_table WHERE leagueId=:leagueId")
    suspend fun getCurrentRound(leagueId: String): CurrentLeagueRound

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagueRounds(leagueRounds: LeagueRounds)

    @Query("SELECT * FROM league_rounds_table WHERE leagueId=:leagueId")
    suspend fun getLeagueRounds(leagueId: String):LeagueRounds

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagueStanding(leagueStanding: LeagueStanding)

    @Query("SELECT * FROM league_standing_table WHERE leagueId=:leagueId")
    suspend fun getLeagueStanding(leagueId: String):LeagueStanding

    @Query("SELECT EXISTS(SELECT * FROM league_standing_table WHERE leagueId=:leagueId)")
    suspend fun leagueStandingExist(leagueId:String):Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagueTopScorers(leagueTopScorers: LeagueTopScorers)

    @Query("SELECT * FROM league_standing_table WHERE leagueId=:leagueId")
    suspend fun getLeagueTopScorers(leagueId: String): LeagueTopScorers

    @Query("SELECT EXISTS(SELECT * FROM league_standing_table WHERE leagueId=:leagueId)")
    suspend fun leagueTopScorersExist(leagueId:String):Boolean


}
package com.siele.matchstats.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siele.matchstats.data.model.stats.scorers.LeagueTopScorers
import com.siele.matchstats.data.model.fixtures.LeagueFixtures
import com.siele.matchstats.data.model.fixtures.CurrentLeagueRound
import com.siele.matchstats.data.model.fixtures.LeagueRounds
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.data.model.standings.LeagueStanding
import com.siele.matchstats.data.model.stats.assists.LeagueTopAssists
import com.siele.matchstats.data.model.teams.LeagueTeams

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

    @Query("SELECT * FROM league_scorers WHERE leagueId=:leagueId")
    suspend fun getLeagueTopScorers(leagueId: String): LeagueTopScorers

    @Query("SELECT EXISTS(SELECT * FROM league_scorers WHERE leagueId=:leagueId)")
    suspend fun leagueSeasonTopScorersExist(leagueId:String):Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagueTopAssists(leagueTopAssists: LeagueTopAssists)

    @Query("SELECT * FROM league_assists WHERE leagueId=:leagueId")
    suspend fun getLeagueTopAssists(leagueId: String): LeagueTopAssists

    @Query("SELECT EXISTS(SELECT * FROM league_assists WHERE leagueId=:leagueId)")
    suspend fun leagueSeasonTopAssistsExist(leagueId:String):Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagueTeams(leagueTeams:LeagueTeams)

    @Query("SELECT * FROM league_teams WHERE leagueId=:leagueId")
    suspend fun getLeagueTeams(leagueId: String): LeagueTeams

    @Query("SELECT EXISTS(SELECT * FROM league_teams WHERE leagueId=:leagueId)")
    suspend fun leagueSeasonTeamsExist(leagueId:String):Boolean


}
package com.siele.matchstats.data.repository

import com.siele.matchstats.data.api.MatchStatsApi
import com.siele.matchstats.data.database.CacheDao
import com.siele.matchstats.data.model.fixtures.CurrentLeagueRound
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.fixtures.LeagueFixtures
import com.siele.matchstats.data.model.fixtures.LeagueRounds
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.data.model.standings.LeagueStanding
import com.siele.matchstats.data.model.standings.Standing
import com.siele.matchstats.data.model.stats.assists.LeagueTopAssists
import com.siele.matchstats.data.model.stats.assists.SeasonTopAssists
import com.siele.matchstats.data.model.stats.scorers.LeagueTopScorers
import com.siele.matchstats.data.model.stats.scorers.SeasonTopScorers
import com.siele.matchstats.data.model.teams.LeagueTeams
import com.siele.matchstats.data.model.teams.SeasonTeams
import com.siele.matchstats.util.Resource
import javax.inject.Inject

class MatchStatsRepository @Inject constructor(
    private val matchStatsApi: MatchStatsApi,
    private val cacheDao: CacheDao
) {
    suspend fun getLeagues(): Resource<List<LeagueResponse>> {
        return try {
            if (cacheDao.getLeagues().isEmpty()) {
                val response = matchStatsApi.getLeagues()
                if (response.isSuccessful) {
                    val list = response.body()?.leagueResponse!!.sortedByDescending { it.seasons.size}
                    cacheDao.insertLeagues(list)
                    Resource.Success(cacheDao.getLeagues())
                } else {
                    Resource.Error("Server error occurred. Please try again later")
                }
            } else {
                Resource.Success(cacheDao.getLeagues())
            }
        } catch (e: Exception) {
            Resource.Error("No internet connection. Please check and try again")
        }
    }

    suspend fun getFixtures(league: String, season: String): Resource<List<FixtureInfo>> {
        return try {
            if (!cacheDao.fixtureExist(league)) {
                val response = matchStatsApi.getFixtures(league = league, season = season)
                val roundResponse = matchStatsApi.getRound(league = league)
                val roundsResponse = matchStatsApi.getLeagueRounds(league = league)

                if (response.isSuccessful) {
                    cacheDao.insertFixtures(
                        LeagueFixtures(
                            leagueId = league,
                            fixtures = response.body()!!.response
                        )
                    )
                    cacheDao.insertCurrentRound(
                        CurrentLeagueRound(
                            leagueId = league,
                            currentRound = roundResponse.body()!!.response.first()
                        )
                    )
                    cacheDao.insertLeagueRounds(LeagueRounds(
                        leagueId = league,
                        rounds = roundsResponse.body()!!.response
                    ))
                    Resource.Success(cacheDao.getFixtures(leagueId = league).fixtures)
                } else {
                    Resource.Error("Server error occurred. Please try again later")
                }
            }else{
                Resource.Success(cacheDao.getFixtures(leagueId = league).fixtures)
            }

        } catch (e: Exception) {
            Resource.Error("No internet connection. Please check and try again")
        }
    }

    suspend fun getRound(leagueId:String) = cacheDao.getCurrentRound(leagueId = leagueId)

    suspend fun getRounds(leagueId:String) =
        cacheDao.getLeagueRounds(leagueId = leagueId)


    suspend fun getStandings(leagueId: String): Resource<List<List<Standing>>>{
        return try {
            if (!cacheDao.leagueStandingExist(leagueId = leagueId)) {
                val response = matchStatsApi.getLeagueStanding(league = leagueId)
                if (response.isSuccessful) {
                    cacheDao.insertLeagueStanding(
                        LeagueStanding(
                            leagueId = leagueId,
                            standings = response.body()!!.response.first().league.standings                        )
                    )
                    Resource.Success(cacheDao.getLeagueStanding(leagueId = leagueId).standings)
                } else {
                    Resource.Error("Server error occurred. Please try again later")
                }
            }else{
                Resource.Success(cacheDao.getLeagueStanding(leagueId = leagueId).standings)
            }

        } catch (e: Exception) {
            Resource.Error("No internet connection. Please check and try again")
        }
    }


    suspend fun getTopScores(leagueId: String, season: String = "2022"): Resource<LeagueTopScorers>{
        return try {
            if (!cacheDao.leagueSeasonTopScorersExist(leagueId = leagueId)) {
                val response = matchStatsApi.getLeagueTopScorers(league = leagueId)
                if (response.isSuccessful) {
                    cacheDao.insertLeagueTopScorers(
                        LeagueTopScorers(
                            leagueId = leagueId,
                            seasonScorers = listOf(
                                SeasonTopScorers(
                                    season = season,
                                    scorers = response.body()!!.response))                      )
                    )
                    Resource.Success(cacheDao.getLeagueTopScorers(leagueId = leagueId))
                } else {
                    Resource.Error("Server error occurred. Please try again later")
                }
            }else{
                Resource.Success(cacheDao.getLeagueTopScorers(leagueId = leagueId))
            }

        } catch (e: Exception) {
            Resource.Error("No internet connection. Please check and try again")
        }
    }


    suspend fun getTopAssists(leagueId: String, season: String ="2022"): Resource<LeagueTopAssists>{
        return try {
            if (!cacheDao.leagueSeasonTopAssistsExist(leagueId = leagueId)) {
                val response = matchStatsApi.getLeagueTopAssists(league = leagueId)
                if (response.isSuccessful) {
                    cacheDao.insertLeagueTopAssists(
                        LeagueTopAssists(
                            leagueId = leagueId,
                            seasonAssists = listOf(
                                SeasonTopAssists(
                                    season = season,
                                    assists = response.body()!!.response))
                        )
                    )
                    Resource.Success(cacheDao.getLeagueTopAssists(leagueId = leagueId))
                } else {
                    Resource.Error("Server error occurred. Please try again later")
                }
            }else{
                Resource.Success(cacheDao.getLeagueTopAssists(leagueId = leagueId))
            }

        } catch (e: Exception) {
            Resource.Error("No internet connection. Please check and try again")
        }
    }


    suspend fun getTeams(leagueId: String, season: String = "2022"): Resource<LeagueTeams>{
        return try {
            if (!cacheDao.leagueSeasonTeamsExist(leagueId = leagueId)) {
                val response = matchStatsApi.getTeams(league = leagueId)
                if (response.isSuccessful) {
                    cacheDao.insertLeagueTeams(
                        LeagueTeams(
                            leagueId = leagueId,
                            seasonTeams = listOf(SeasonTeams(season = season, teams = response.body()!!.response))                         )
                    )
                    Resource.Success(cacheDao.getLeagueTeams(leagueId = leagueId))
                } else {
                    Resource.Error("Server error occurred. Please try again later")
                }
            }else{
                Resource.Success(cacheDao.getLeagueTeams(leagueId = leagueId))
            }

        } catch (e: Exception) {
            Resource.Error("No internet connection. Please check and try again")
        }
    }

}
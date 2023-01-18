package com.siele.matchstats.data.repository

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.siele.matchstats.data.api.MatchStatsApi
import com.siele.matchstats.data.database.CacheDao
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.fixtures.LeagueFixtures
import com.siele.matchstats.data.model.fixtures.CurrentLeagueRound
import com.siele.matchstats.data.model.fixtures.LeagueRounds
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.data.model.standings.LeagueStanding
import com.siele.matchstats.data.model.standings.Standing
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


    suspend fun getStandings(leagueId: String): Resource<List<Standing>>{
        return try {
            if (!cacheDao.leagueStandingExist(leagueId = leagueId)) {
                val response = matchStatsApi.getLeagueStanding(league = leagueId)
                if (response.isSuccessful) {
                    cacheDao.insertLeagueStanding(
                        LeagueStanding(
                            leagueId = leagueId,
                            standings = response.body()!!.response.first().league.standings.first()
                        )
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

}
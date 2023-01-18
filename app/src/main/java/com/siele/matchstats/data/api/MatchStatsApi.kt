package com.siele.matchstats.data.api

import com.siele.matchstats.data.model.fixtures.FixturesResponse
import com.siele.matchstats.data.model.fixtures.RoundsResponse
import com.siele.matchstats.data.model.leagues.LeaguesResponse
import com.siele.matchstats.data.model.standings.StandingsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.TimeZone

interface MatchStatsApi {
    @GET("leagues")
    suspend fun getLeagues():Response<LeaguesResponse>

    @GET("fixtures")
    suspend fun getFixtures(
        @Query("league") league:String ="",
        @Query("season") season:String = "2022",
        @Query("timezone") timeZone:String = TimeZone.getDefault().id
    ):Response<FixturesResponse>

    @GET("fixtures/rounds")
    suspend fun getRound(
        @Query("league") league: String = "",
        @Query("current") current: Boolean = true,
        @Query("season") season: String = "2022",
    ):Response<RoundsResponse>

    @GET("fixtures/rounds")
    suspend fun getLeagueRounds(
        @Query("league") league: String = "",
        @Query("season") season: String = "2022",
    ):Response<RoundsResponse>


    @GET("standing")
    suspend fun getLeagueStanding(
        @Query("league") league: String = "",
        @Query("season") season: String = "2022",
    ):Response<StandingsResponse>
}
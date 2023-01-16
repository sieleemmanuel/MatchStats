package com.siele.matchstats.data.api

import com.siele.matchstats.data.model.fixtures.FixturesResponse
import com.siele.matchstats.data.model.leagues.LeaguesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.SimpleTimeZone
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
}
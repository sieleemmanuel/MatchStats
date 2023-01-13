package com.siele.matchstats.data.api

import com.siele.matchstats.data.model.LeaguesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MatchStatsApi {
    @GET("leagues")
    suspend fun getLeagues():Response<LeaguesResponse>
}
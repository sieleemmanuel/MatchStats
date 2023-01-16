package com.siele.matchstats.data.repository

import com.siele.matchstats.data.api.MatchStatsApi
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.util.Resource

class MatchStatsRepository(private val matchStatsApi: MatchStatsApi) {
    suspend fun getLeagues():Resource<List<LeagueResponse>>{
      return  try {
          val response = matchStatsApi.getLeagues()
          if (response.isSuccessful){
              Resource.Success(response.body()!!.leagueResponse)
          }else{
              Resource.Error("Server error occurred. Please try again later")
          }
        }catch (e:Exception){
            Resource.Error("No internet connection. Please check and try again")
        }
    }

    suspend fun getFixtures(league:String, season:String):Resource<List<FixtureInfo>>{
        return  try {
            val response = matchStatsApi.getFixtures(league = league, season = season)
            if (response.isSuccessful){
                Resource.Success(response.body()!!.response)
            }else{
                Resource.Error("Server error occurred. Please try again later")
            }
        }catch (e:Exception){
            Resource.Error("No internet connection. Please check and try again")
        }
    }
}
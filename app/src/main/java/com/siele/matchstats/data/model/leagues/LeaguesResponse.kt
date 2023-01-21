package com.siele.matchstats.data.model.leagues

import com.google.gson.annotations.SerializedName
import com.siele.matchstats.data.model.common.Paging

data class LeaguesResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: List<Any>,
    @SerializedName("response")
    val leagueResponse: List<LeagueResponse>,
    val results: Int
)
package com.siele.matchstats.data.model.standings

import com.siele.matchstats.data.model.common.Parameters
import com.siele.matchstats.data.model.common.Paging

data class StandingsResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)
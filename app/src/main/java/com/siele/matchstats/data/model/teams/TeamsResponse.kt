package com.siele.matchstats.data.model.teams

import com.siele.matchstats.data.model.common.Parameters
import com.siele.matchstats.data.model.common.Paging

data class TeamsResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<TeamResponse>,
    val results: Int
)
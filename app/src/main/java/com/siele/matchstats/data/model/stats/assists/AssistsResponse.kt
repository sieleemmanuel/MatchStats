package com.siele.matchstats.data.model.stats.assists

import com.siele.matchstats.data.model.common.Paging
import com.siele.matchstats.data.model.common.Parameters
import com.siele.matchstats.data.model.stats.PlayerResponse

data class AssistsResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<PlayerResponse>,
    val results: Int
)
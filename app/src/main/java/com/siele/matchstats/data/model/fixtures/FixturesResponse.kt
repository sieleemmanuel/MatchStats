package com.siele.matchstats.data.model.fixtures

import com.siele.matchstats.data.model.common.Parameters
import com.siele.matchstats.data.model.common.Paging

data class FixturesResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<FixtureInfo>,
    val results: Int
)
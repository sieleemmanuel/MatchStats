package com.siele.matchstats.data.repository

import com.siele.matchstats.data.api.MatchStatsApi
import com.siele.matchstats.data.model.*
import com.siele.matchstats.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.hamcrest.core.IsEqual
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.anyArray
import retrofit2.Response

@RunWith(JUnit4::class)
class MatchStatsRepositoryTest {
    @Mock
    lateinit var matchStatsApi: MatchStatsApi
    lateinit var matchStatsRepository: MatchStatsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        matchStatsRepository = MatchStatsRepository(matchStatsApi)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Suppress("DEPRECATION")
    fun `get leagues request should return success response`(){
        runTest {
            val country = Country("1","","country")
            val seasons = listOf<Season>()
            val league = League(39,"","Premier league", "League")
            val leaguesList = listOf(LeagueResponse(country, league, seasons))
            val leaguesResponse = LeaguesResponse(
                errors = emptyList(),
                get = "",
                paging = Paging(1,1),
                parameters = emptyList(),
                leagueResponse = leaguesList,
                results = 904
            )
            `when`(matchStatsApi.getLeagues()).thenReturn(Response.success(leaguesResponse))
            val response = matchStatsRepository.getLeagues()
            assertThat(leaguesList, IsEqual(response.data))
        }

    }
}
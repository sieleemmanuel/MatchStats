package com.siele.matchstats.ui.screens

import com.siele.matchstats.data.model.Country
import com.siele.matchstats.data.model.League
import com.siele.matchstats.data.model.LeagueResponse
import com.siele.matchstats.data.model.Season
import com.siele.matchstats.data.repository.MatchStatsRepository
import com.siele.matchstats.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@Suppress("DEPRECATION")
class MainViewModelTest{
    @Mock
    lateinit var matchStatsRepository: MatchStatsRepository
    lateinit var mainViewModel: MainViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(matchStatsRepository)
    }

    @Test
    fun `get league list with no error should return success response state`(){
        runTest {
            val leagues = listOf<LeagueResponse>()
            `when`(matchStatsRepository.getLeagues()).thenReturn(Resource.Success(leagues))
            mainViewModel.getLeagues()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = mainViewModel.leaguesState.value
            assertThat(Resource.Success(leagues).data, IsEqual(result.data))

        }

    }

    @Test
    fun `get league list with server error should return error response state`(){
        runTest {
            val errorMessage = "Server error occurred. Please try again later"
            `when`(matchStatsRepository.getLeagues())
                .thenReturn(Resource.Error("Server error occurred. Please try again later"))
            mainViewModel.getLeagues()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = mainViewModel.leaguesState.value
            assertThat(errorMessage, IsEqual(result.message))
        }

    }

    @Test
    fun `get league list with no network should return network error response`(){
        runTest {
            val errorMessage = "No internet connection. Please check and try again"
            `when`(matchStatsRepository.getLeagues())
                .thenReturn(Resource.Error("No internet connection. Please check and try again"))
            mainViewModel.getLeagues()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = mainViewModel.leaguesState.value
            assertThat(errorMessage, IsEqual(result.message))
        }

    }

    @Test
    fun `filter leagues should return a filter list`(){
        val country = Country("1","","country")
        val seasons = listOf<Season>()
        val league = League(39,"","Premier league", "League")
        val leaguesList = listOf(LeagueResponse(country, league, seasons))
        val filtered = mainViewModel.filterLeagues("Premier",leaguesList).first()
        assert(leaguesList.contains(filtered))
    }
}
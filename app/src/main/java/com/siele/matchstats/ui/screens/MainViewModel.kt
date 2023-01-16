package com.siele.matchstats.ui.screens

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.data.repository.MatchStatsRepository
import com.siele.matchstats.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MatchStatsRepository):ViewModel() {

    val isSearchActive = mutableStateOf(false)
    private val _leagueState = MutableStateFlow<Resource<List<LeagueResponse>>>(Resource.InitState())
    val leaguesState:StateFlow<Resource<List<LeagueResponse>>> = _leagueState
    val listState = mutableStateOf<Resource<List<LeagueResponse>>>(Resource.InitState())


    val fixtures = mutableStateOf<Resource<List<FixtureInfo>>>(Resource.InitState())

    private val _fixturesState = MutableStateFlow<Resource<List<FixtureInfo>>>(Resource.InitState())
    val fixturesState:StateFlow<Resource<List<FixtureInfo>>> = _fixturesState

    fun getLeagues(){
        viewModelScope.launch {
            _leagueState.value = Resource.Loading()
            _leagueState.value = repository.getLeagues()
        }
    }

    fun filterLeagues(query:String, leagues:List<LeagueResponse>):List<LeagueResponse>{
        return leagues.filter {
            it.league.name.lowercase(Locale.getDefault()).contains(
            query.lowercase(Locale.getDefault())
        ) }
    }

    fun getFixtures(league: String, season: String){
        viewModelScope.launch {
            _fixturesState.value = Resource.Loading()
            _fixturesState.value = repository.getFixtures(league, season)
            /*fixtures.value = Resource.Loading()
            fixtures.value = repository.getFixtures(league, season)*/
        }
    }

    fun getLeague(leagueName: String){
        viewModelScope.launch {
            leaguesState.collect{ leaguesResponse ->
                leaguesResponse.data?.find { it.league.name == leagueName  }
            }
        }

    }

    init {
        getLeagues()
    }

}
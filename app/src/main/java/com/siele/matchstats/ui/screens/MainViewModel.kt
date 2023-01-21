package com.siele.matchstats.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.fixtures.CurrentLeagueRound
import com.siele.matchstats.data.model.fixtures.LeagueRounds
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.data.model.standings.Standing
import com.siele.matchstats.data.model.stats.assists.LeagueTopAssists
import com.siele.matchstats.data.model.stats.scorers.LeagueTopScorers
import com.siele.matchstats.data.model.teams.LeagueTeams
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

    val fixtures = mutableStateOf<Resource<List<FixtureInfo>>>(Resource.InitState())

    private val _fixturesState = MutableStateFlow<Resource<List<FixtureInfo>>>(Resource.InitState())
    val fixturesState:StateFlow<Resource<List<FixtureInfo>>> = _fixturesState

    private val _currentLeagueRound: MutableStateFlow<CurrentLeagueRound?> = MutableStateFlow(null)
    val currentLeagueRound:StateFlow<CurrentLeagueRound?> = _currentLeagueRound

    private val _leagueRounds : MutableStateFlow<LeagueRounds?> = MutableStateFlow(null)
    val leagueRounds:StateFlow<LeagueRounds?> = _leagueRounds

    private val _standingState = MutableStateFlow<Resource<List<List<Standing>>>>(Resource.InitState())
    val standingState:StateFlow<Resource<List<List<Standing>>>> = _standingState

    private val _topScorersState = MutableStateFlow<Resource<LeagueTopScorers>>(Resource.InitState())
    val topScorersState:StateFlow<Resource<LeagueTopScorers>> = _topScorersState

    private val _topAssistsState = MutableStateFlow<Resource<LeagueTopAssists>>(Resource.InitState())
    val topAssistsState:StateFlow<Resource<LeagueTopAssists>> = _topAssistsState

    private val _teamsState = MutableStateFlow<Resource<LeagueTeams>>(Resource.InitState())
    val teamsState:StateFlow<Resource<LeagueTeams>> = _teamsState

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
            _currentLeagueRound.value = repository.getRound(leagueId = league)
            _leagueRounds.value = repository.getRounds(leagueId=league)

        }
    }

    fun getStanding(league: String, season: String = "2022"){
        viewModelScope.launch {
            _standingState.value = repository.getStandings(league)
        }
    }

    fun getTopScorers(league: String, season: String = "2022"){
        viewModelScope.launch {
            _topScorersState.value = repository.getTopScores(leagueId = league)
        }
    }
    fun getTopAssists(league: String, season: String = "2022"){
        viewModelScope.launch {
            _topAssistsState.value = repository.getTopAssists(leagueId = league)
        }
    }

    fun getTeams(league: String, season: String = "2022"){
        viewModelScope.launch {
            _teamsState.value = repository.getTeams(leagueId = league)
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
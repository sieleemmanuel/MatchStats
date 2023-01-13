package com.siele.matchstats.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siele.matchstats.data.model.LeagueResponse
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

    init {
        getLeagues()
    }

}
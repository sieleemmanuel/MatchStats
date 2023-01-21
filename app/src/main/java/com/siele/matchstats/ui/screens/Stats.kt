package com.siele.matchstats.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.siele.matchstats.R
import com.siele.matchstats.data.model.stats.PlayerResponse
import com.siele.matchstats.util.Resource

@Composable
fun StatsTab(mainViewModel: MainViewModel = hiltViewModel(), leagueId: String) {
    mainViewModel.getTopScorers(league = leagueId)
    val topScorersState = mainViewModel.topScorersState.collectAsState().value
    val topAssistsState = mainViewModel.topAssistsState.collectAsState().value
    Box(modifier = Modifier.fillMaxSize()) {
        when(topScorersState){
            is Resource.Success ->{
                val topScorers = topScorersState.data?.seasonScorers?.find {
                    it.season == "2022"  }?.scorers?.take(10)
                Column(modifier = Modifier.padding(all = 16.dp)) {
                    LazyColumn {
                        item {
                           // Column(modifier = Modifier.padding(all = 16.dp)) { }
                            Text(text = "Goals")
                            Spacer(modifier = Modifier.heightIn(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Player")
                                Text(text = "Goals")
                            }
                            Spacer(modifier = Modifier.heightIn(16.dp))
                        }

                        itemsIndexed(items = topScorers!!) { index,scorer ->
                            PlayerRow(scorer, rank = index.plus(1) )
                        }

                        item {
                            // Column(modifier = Modifier.padding(all = 16.dp)) { }
                            Spacer(modifier = Modifier.heightIn(16.dp))
                            Text(text = "Assists")
                            Spacer(modifier = Modifier.heightIn(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Player")
                                Text(text = "Assists")
                            }
                            Spacer(modifier = Modifier.heightIn(16.dp))
                        }

                       when(topAssistsState){
                           is Resource.Success ->{
                               val topAssists = topAssistsState.data?.seasonAssists?.find {
                               it.season == "2022"  }?.assists?.take(10)
                               itemsIndexed(items = topAssists!!) { index, assists ->
                                   PlayerAssistRow(assists, rank = index.plus(1) )
                               }}
                           is Resource.Error ->{
                               Log.d("Stats", "StatsTab: ")
                               item {
                                   ErrorStateCompose(errorMessage = topAssistsState.message!!) {
                                       mainViewModel.getTopAssists(leagueId)
                                   }
                               }
                           }
                           is Resource.Loading ->{
                               item {
                                   Column(modifier = Modifier
                                       .wrapContentHeight()
                                       .padding(10.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally) {
                                       CircularProgressIndicator(
                                           modifier = Modifier.size(40.dp),
                                           color = MaterialTheme.colors.primary
                                       )
                                   }

                               }

                           }
                           else -> {
                               item {
                                   Spacer(modifier = Modifier.height(10.dp))
                                   Text(text = "Nothing to show right")
                                   Spacer(modifier = Modifier.height(10.dp))

                               }
                           }
                       }




                    }

                }
            }
            is Resource.Error ->{
                ErrorStateCompose(errorMessage = topScorersState.message!!) {
                    mainViewModel.getTopScorers(league = leagueId)
                }
            }
            is Resource.Loading ->{
                LoadingStateCompose()
            }

        }

    }
}

@Composable
fun PlayerRow(playerInfo: PlayerResponse, rank:Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val profile = rememberImagePainter(
            data = playerInfo.player.photo,
            builder = {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        )
        val teamLogo = rememberImagePainter(
            data = playerInfo.statistics.first().team.logo,
            builder = {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = rank.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = profile,
                contentDescription = "Player Profile",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = playerInfo.player.name)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = teamLogo,
                            contentDescription = "Player Profile",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = playerInfo.statistics.first().team.name)
                    }

                }
                Text(text = playerInfo.statistics.first().goals.total.toString())
            }

        }

    }
}


@Composable
fun PlayerAssistRow(playerAssistInfo: PlayerResponse, rank:Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val profile = rememberImagePainter(
            data = playerAssistInfo.player.photo,
            builder = {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        )
        val teamLogo = rememberImagePainter(
            data = playerAssistInfo.statistics.first().team.logo,
            builder = {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = rank.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = profile,
                contentDescription = "Player Profile",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = playerAssistInfo.player.name)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = teamLogo,
                            contentDescription = "Player Profile",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = playerAssistInfo.statistics.first().team.name)
                    }

                }
                Text(text = playerAssistInfo.statistics.first().goals.assists.toString())
            }

        }

    }
}

/*
@Preview(
    showSystemUi = true
)
@Composable
fun StatsPreview() {
    MatchStatsTheme {
        StatsTab(leagueId = "39")
    }

}

*/

package com.siele.matchstats.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.siele.matchstats.R
import com.siele.matchstats.data.model.teams.TeamResponse
import com.siele.matchstats.util.Resource

@Composable
fun TeamsTab(mainViewModel: MainViewModel = hiltViewModel(), leagueId: String) {
    mainViewModel.getTeams(league = leagueId)
    val context = LocalContext.current
    when (val teamsState = mainViewModel.teamsState.collectAsState().value) {
        is Resource.Loading -> {
            LoadingStateCompose()
        }
        is Resource.Success -> {
            val teams = teamsState.data?.seasonTeams?.first { it.season == "2022" }?.teams
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(items = teams!!) { team ->
                    TeamRow(team) { clickedTeam ->
                        Toast.makeText(context, clickedTeam.team.name, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        is Resource.Error -> {
            ErrorStateCompose(errorMessage = teamsState.message!!) {
                mainViewModel.getTeams(leagueId)
            }
        }
        else -> {}
    }

}

@Composable
fun TeamRow(team: TeamResponse, clickedTeam: (TeamResponse) -> Unit) {
    val teamLogo = rememberImagePainter(
        data = team.team.logo,
        builder = {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    )

    Card(
        modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(160.dp)
                .clickable { clickedTeam(team) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = teamLogo,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = team.team.name,
                modifier = Modifier.padding(5.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }


}

/*
@Preview(showSystemUi = true)
@Composable
fun TeamsPreview() {
    MatchStatsTheme {
        TeamsTab()
    }

}
*/

package com.siele.matchstats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.siele.matchstats.ui.theme.MatchStatsTheme
import com.siele.matchstats.R
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.util.Resource
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchesTab(league:String) {
    Box(modifier = Modifier.fillMaxSize()){
        val listState = rememberLazyListState()
        val mainViewModel:MainViewModel = hiltViewModel()
        when(val fixturesInfo =  mainViewModel.fixturesState.collectAsState().value){
            is Resource.Success -> {
                val groupedFixtures = fixturesInfo.data?.groupBy {
                    val round = it.league.round.split("-").last().trim().toInt()
                    round }
                val index = groupedFixtures?.entries?.indexOfFirst { it.value.any { it.fixture.status.short == "NS" } }
                LaunchedEffect(key1 = true ){
                    listState.animateScrollToItem(index = index?:0)
                }

                LazyColumn(
                    state = listState,
                    userScrollEnabled = true,
                    contentPadding = PaddingValues(
                        vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    groupedFixtures!!.forEach { (round, fixtures) ->
                        stickyHeader {
                            Text(
                                modifier = Modifier
                                    .background(color = MaterialTheme.colors.surface)
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp, horizontal = 10.dp),
                                text = "Match $round of 38"
                            )
                        }
                        items(items = fixtures.sortedBy { it.fixture.timestamp }){ fixture ->
                            MatchRow(fixture)
                        }
                    }
                }

            }
            is Resource.Error -> {
                ErrorStateCompose( errorMessage = fixturesInfo.message!!) {
                    mainViewModel.getFixtures(league = league, season = "2022")
                }
            }
            is Resource.Loading -> {
                LoadingStateCompose()
            }
            else ->{

            }
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchRow(fixtureInfo: FixtureInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val homeLogo = rememberImagePainter(
                data = fixtureInfo.teams.home.logo,
                builder = {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            )
            val awayLogo = rememberImagePainter(
                data = fixtureInfo.teams.away.logo,
                builder = {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            )

            Column (modifier = Modifier.padding(start = 10.dp)){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = homeLogo,
                        contentDescription = "Home logo" ,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                    )
                    Text(
                        text = fixtureInfo.teams.home.name,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Row {
                    Image(
                        painter = awayLogo,
                        contentDescription = "away logo" ,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                    )
                    Text(
                        text = fixtureInfo.teams.away.name,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            Row (
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column {
                    if (fixtureInfo.fixture.status.elapsed != null) {
                        Text(
                            text = fixtureInfo.score.fulltime.home.toString(),
                            modifier = Modifier.padding(10.dp),
                            fontWeight = if (fixtureInfo.teams.home.winner != null && fixtureInfo.teams.home.winner) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                        Text(
                            text = fixtureInfo.score.fulltime.away.toString(),
                            modifier = Modifier.padding(10.dp),
                            fontWeight = if (fixtureInfo.teams.away.winner != null && fixtureInfo.teams.away.winner) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                }

                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .width(2.dp)
                        .height(80.dp),
                    thickness = 1.dp
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    if(fixtureInfo.fixture.status.short == "FT"){
                        Text(text = fixtureInfo.fixture.status.short)
                    }
                    Text(text = formattedDate(fixtureInfo.fixture.date))
                    if(fixtureInfo.fixture.status.short != "FT") {
                        Text(text = formattedTime(fixtureInfo.fixture.date))
                    }
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun formattedDate(dateString: String): String {
    val inputDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
    return inputDate.format(DateTimeFormatter.ofPattern("EEE, dd MMM"))

}

@RequiresApi(Build.VERSION_CODES.O)
fun formattedTime(dateString: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val inputFormatter =   SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = inputFormatter.parse(dateString)!!
        outputFormatter.format(date)
    } else {
        val inputFormatter =   SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val date = inputFormatter.parse(dateString)!!
        val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"))
        val hour = zonedDateTime.hour.toString().padStart(2,'0')
        val minute = zonedDateTime.minute.toString().padStart(2,'0')
        "$hour:$minute"
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showSystemUi = true
)
@Composable
fun MatchesPreview() {
    MatchStatsTheme {
       // MatchesTab("premier league", )
    }

}
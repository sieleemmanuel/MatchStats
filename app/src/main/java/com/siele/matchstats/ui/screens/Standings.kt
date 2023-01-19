package com.siele.matchstats.ui.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.siele.matchstats.R
import com.siele.matchstats.data.model.standings.Standing
import com.siele.matchstats.ui.theme.MatchStatsTheme
import com.siele.matchstats.util.Constants
import com.siele.matchstats.util.Resource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StandingsTab(modifier: Modifier = Modifier, leagueId: String, type: String) {
    val horizontalScrollState = rememberScrollState()
    val mainViewModel:MainViewModel = hiltViewModel()
    val standingsState = mainViewModel.standingState.collectAsState().value
    val TAG = "Standing"
    Log.d(TAG, "Standings List: ${standingsState.data}")
    Log.d(TAG, "Standings Message: ${standingsState.message}")
    Box(modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier.fillMaxWidth() ){
            SeasonsDropdownMenu()

            when(standingsState){
                is Resource.Success ->{
                    if (type == "League") {
                        val standings = standingsState.data!!.first()
                        Row (modifier = Modifier
                            .horizontalScroll(horizontalScrollState)
                                ){
                            Text(
                                text = "Club",
                                modifier = Modifier
                                    .width(225.dp)
                                    .padding(start = 10.dp),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "MP", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "W", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "D", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "L", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "Pts", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "GF", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "GA", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "GD", textAlign = TextAlign.Center,
                                modifier = modifier.width(30.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                        }

                        LazyColumn(modifier = modifier.fillMaxWidth()) {
                            itemsIndexed(items = standings) { index, clubStanding ->

                                val clubLogo = rememberImagePainter(
                                    data = clubStanding.team.logo,
                                    builder = {
                                        placeholder(R.drawable.loading_animation)
                                        error(R.drawable.ic_broken_image)
                                    }
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .horizontalScroll(horizontalScrollState),
                                    verticalAlignment = Alignment.CenterVertically,

                                    ) {
                                    Text(
                                        text = "${index.plus(1)}",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .width(20.dp),
                                        maxLines = 1
                                    )
                                    Image(
                                        painter = clubLogo,
                                        contentDescription = "Home logo",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(5.dp)
                                    )
                                    Text(
                                        text = clubStanding.team.name,
                                        modifier = Modifier
                                            .width(150.dp)
                                            .padding(start = 5.dp),
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = clubStanding.all.played.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = clubStanding.all.win.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = clubStanding.all.draw.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = clubStanding.all.lose.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = clubStanding.points.toString(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = clubStanding.all.goals.`for`.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = clubStanding.all.goals.against.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = clubStanding.goalsDiff.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))

                                    Text(
                                        text = clubStanding.form,
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.width(50.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                }

                            }
                        }
                    }else{
                        val standings = standingsState.data!!.flatten().groupBy { it.group }
                        LazyColumn{
                            standings.forEach { (group, teamStanding) ->
                                stickyHeader {
                                    Column (modifier = modifier.background(MaterialTheme.colors.surface)){
                                        Spacer(modifier = modifier.height(10.dp))
                                        Text(
                                            text = group,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            modifier = modifier.padding(start = 10.dp)
                                        )
                                        Row (modifier = Modifier
                                            .horizontalScroll(horizontalScrollState)
                                        ){
                                            Text(
                                                text = "Club",
                                                modifier = Modifier
                                                    .width(225.dp)
                                                    .padding(start = 10.dp),
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 1
                                            )
                                            Text(text = "MP", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp))
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = "W", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp))
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = "D", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp))
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = "L", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp))
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = "Pts", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp), fontWeight = FontWeight.Bold)
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = "GF", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp))
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = "GA", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp))
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = "GD", textAlign = TextAlign.Center,
                                                modifier = modifier.width(30.dp))
                                            Spacer(modifier = Modifier.width(20.dp))
                                        }
                                    }
                                }
                                items(items = teamStanding){  teamStanding1 ->
                                    val clubLogo = rememberImagePainter(
                                        data = teamStanding1.team.logo,
                                        builder = {
                                            placeholder(R.drawable.loading_animation)
                                            error(R.drawable.ic_broken_image)
                                        }
                                    )

                                    Row(
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                            .horizontalScroll(horizontalScrollState),
                                        verticalAlignment = Alignment.CenterVertically,

                                        ) {
                                        Text(
                                            text = teamStanding1.rank.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .width(20.dp),
                                            maxLines = 1
                                        )
                                        Image(
                                            painter = clubLogo,
                                            contentDescription = "Home logo",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .padding(5.dp)
                                        )
                                        Text(
                                            text = teamStanding1.team.name,
                                            modifier = Modifier
                                                .width(150.dp)
                                                .padding(start = 5.dp),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = teamStanding1.all.played.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = teamStanding1.all.win.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = teamStanding1.all.draw.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = teamStanding1.all.lose.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = teamStanding1.points.toString(),
                                            fontWeight = FontWeight.Bold,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = teamStanding1.all.goals.`for`.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = teamStanding1.all.goals.against.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = teamStanding1.goalsDiff.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(30.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))

                                        Text(
                                            text = teamStanding1.form,
                                            textAlign = TextAlign.Center,
                                            modifier = modifier.width(50.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                    }
                                }
                            }

                        }

                    }
                }
                is Resource.Error -> {
                    ErrorStateCompose(errorMessage = standingsState.message!!) {
                        mainViewModel.getStanding(league = leagueId)
                    }
                }
                is Resource.Loading -> {
                    LoadingStateCompose()
                }
                else -> {Unit}
            }

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SeasonsDropdownMenu() {
    var isMenuExpanded by rememberSaveable { mutableStateOf(false)}
    var selectedSeason by rememberSaveable { mutableStateOf(Constants.seasons[Constants.seasons.size - 1])}

    ExposedDropdownMenuBox(
        expanded = isMenuExpanded ,
        onExpandedChange = {
            isMenuExpanded = !isMenuExpanded
        },
        modifier = Modifier.fillMaxWidth()
        ) {
        TextField(
            value = selectedSeason,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMenuExpanded)
            },
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            label = { Text(text = "Season")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray),
            visualTransformation = VisualTransformation.None,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                trailingIconColor = Color.LightGray,
                textColor = Color.LightGray,
                focusedLabelColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray
            )
        )
        ExposedDropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {

            Constants.seasons.reversed().forEach { season->
                DropdownMenuItem(onClick = {
                selectedSeason = season
                    isMenuExpanded = false
                }) {
                    Text(text = season)
                }
            }
        }
}
}

/*
@Preview(
    showSystemUi = true
)
@Composable
fun TablePreview() {
    MatchStatsTheme {
        StandingsTab()
    }
}*/

package com.siele.matchstats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siele.matchstats.ui.theme.MatchStatsTheme
import com.siele.matchstats.R

@Composable
fun MatchesTab() {
    Box(modifier = Modifier.fillMaxSize()){
        val listState = rememberLazyListState()
        val fixture = mutableListOf("Match one","Match one",
            "Match Two", "Match Three", "Match Four",
            "Match Five","Match Six", "Match Seven","Match Eight",
            "Match Nine")
        LazyColumn(
            state = listState,
            userScrollEnabled = true,
            contentPadding = PaddingValues(
                vertical = 10.dp,
                horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(fixture.size){ item ->
                MatchRow(fixture[item])
            }
        }
    }

}

@Composable
fun MatchRow(match: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (modifier = Modifier.padding(start = 10.dp)){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Team A logo" ,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                    )
                    Text(
                        text = "Team A",
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Team B logo" ,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                    )
                    Text(
                        text = "Team B",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            Row (
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column {
                    Text(
                        text = "5",
                        modifier = Modifier.padding(10.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "3",
                        modifier = Modifier.padding(10.dp),
                        fontWeight = FontWeight.Bold
                    )
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
                    Text(text = "Sat, 15/10")
                    Text(text = "14:30")
                }
            }
        }

    }

}

@Preview(
    showSystemUi = true
)
@Composable
fun MatchesPreview() {
    MatchStatsTheme {
        MatchesTab()
    }

}
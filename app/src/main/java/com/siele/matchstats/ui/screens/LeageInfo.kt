package com.siele.matchstats.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.*
import com.siele.matchstats.ui.theme.MatchStatsTheme
import kotlinx.coroutines.launch

@Composable
fun LeagueInfo(navController: NavController, leagueName: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { LeagueInfoTopBar(
                title = leagueName!!,
                navController = navController
            ) },
            modifier = Modifier
                .fillMaxSize()
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                TabLayout()
            }

        }
    }
}

@Composable
fun LeagueInfoTopBar(title:String, navController: NavController) {
    TopAppBar(
        title = { Text(text = title)},
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBack,"ArrowBack")
            }
        },
        elevation = 0.dp
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout() {
    val pageState = rememberPagerState(0)
    Tabs(pagerState = pageState)
    TabsContents(pagerState = pageState)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val tabs = listOf("Matches", "Standings", "Stats", "Teams", "Players" )
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        edgePadding = 16.dp,
        indicator = { tabPositions ->
        TabRowDefaults.Indicator(
            modifier = Modifier.pagerTabIndicatorOffset(
                pagerState = pagerState,
                tabPositions = tabPositions),
            height = 3.dp,
            color = MaterialTheme.colors.onPrimary
        )
        }
    ) {
        tabs.forEachIndexed { index, _ ->
            Tab(
                selected = pagerState.currentPage==index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = tabs[index],
                        color = if (pagerState.currentPage==index) Color.White else Color.LightGray,
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1
                    )
                }
            )

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContents(pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = 5, userScrollEnabled = true) { page ->
    when(page){
        0 -> MatchesTab()
        1 -> TableTab()
        2 -> StatsTab()
        3 -> PlayersTab()
        4 -> TeamsTab()
    }

    }

}

@Preview(showSystemUi = true)
@Composable
fun LeagueInfoPreview() {
    MatchStatsTheme {
        LeagueInfo(rememberNavController(), leagueName= "Premier League")
    }

}

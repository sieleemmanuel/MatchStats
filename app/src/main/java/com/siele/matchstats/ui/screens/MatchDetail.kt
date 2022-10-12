package com.siele.matchstats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.siele.matchstats.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.*
import com.siele.matchstats.ui.screens.matchinfo.LineupsTab
import com.siele.matchstats.ui.screens.matchinfo.MatchStatsTab
import com.siele.matchstats.ui.screens.matchinfo.MatchTableTab
import com.siele.matchstats.ui.theme.MatchStatsTheme
import kotlinx.coroutines.launch

@Composable
fun MatchDetail(cupName:String, navController:NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { LeagueInfoTopBar(
                title = cupName,
                navController = navController,
            ) },
            modifier = Modifier
                .fillMaxSize()
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TeamNameLogo(R.drawable.ic_launcher_background, "Brentford")

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Fri, 14 Oct")
                        Text(text = "22:00")

                    }
                    TeamNameLogo(R.drawable.ic_launcher_background, "Brighton")

                }
                MatchTabLayout()

            }

        }
    }
}

@Composable
private fun TeamNameLogo(logo:Int, teamName:String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = logo),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
        )
        Text(text = teamName)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchTabLayout() {
    val pagerState  = rememberPagerState()
    MatchTabs(pagerState = pagerState)
    MatchTabsContents(pagerState = pagerState)

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchTabs(pagerState: PagerState) {
    val tabs = listOf("Lineups", " Match Statistics", "Table"  )
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 16.dp,
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(
                    pagerState = pagerState,
                    tabPositions = tabPositions),
                height = 0.dp,
                color = Color.Transparent
            )
        },
        divider = { }
    ) {
        tabs.forEachIndexed { index, _ ->
            Tab(
                selected = pagerState.currentPage==index,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.LightGray,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = tabs[index],
                        color = if (pagerState.currentPage==index) MaterialTheme.colors.primary else Color.DarkGray,
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1
                    )
                },
                modifier = Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(30.dp))
            )

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchTabsContents(pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = 5, userScrollEnabled = true) { page ->
        when(page){
            0 -> LineupsTab()
            1 -> MatchStatsTab()
            2 -> MatchTableTab()
        }

    }

}


@Preview(
    showSystemUi = true
)
@Composable
fun MatchDetailPreview() {
    MatchStatsTheme {
        MatchDetail(
            cupName = "UEFA Champions League",
            navController = rememberNavController()
        )
    }
}


package com.siele.matchstats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.siele.matchstats.R
import com.siele.matchstats.data.model.League
import com.siele.matchstats.ui.theme.MatchStatsTheme
import com.siele.matchstats.util.Constants
import com.siele.matchstats.util.Screen

@Composable
fun DashboardContent(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { DashboardTopBar() },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            ListLeagues(paddingValues, navController)
        }
    }

}

@Composable
fun DashboardTopBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 150.dp),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Choose league or tournament",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(15.dp),
            )
            SearchBar(Modifier)
        }

    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = {
            query = it
        },

        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            textColor = Color.DarkGray,
        ),
        placeholder = {
            Text(text = stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun ListLeagues(paddingValues: PaddingValues, navController: NavController) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(Constants.leagues.size) { position ->
            ItemRow(Constants.leagues[position]) { selectedLeague ->
                navController.navigate(Screen.LeagueInfoScreen.route + "/${selectedLeague.name}")
            }
        }
    }

}

@Composable
fun ItemRow(league: League, selectedLeague: (League) -> Unit) {
    Card(
        modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(160.dp)
                .clickable { selectedLeague(league) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_launcher_background
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = league.name,
                modifier = Modifier.padding(5.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    MatchStatsTheme {
        DashboardContent(rememberNavController())
    }

}
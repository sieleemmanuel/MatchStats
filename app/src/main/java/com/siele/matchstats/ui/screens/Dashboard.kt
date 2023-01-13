package com.siele.matchstats.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.siele.matchstats.R
import com.siele.matchstats.data.model.League
import com.siele.matchstats.ui.theme.MatchStatsTheme
import com.siele.matchstats.util.Constants
import com.siele.matchstats.util.Resource
import com.siele.matchstats.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun DashboardContent(navController: NavController) {
    val focusManager = LocalFocusManager.current
    val query = rememberSaveable{
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { DashboardTopBar(focusManager =focusManager, query = query) },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            ListLeagues(paddingValues = paddingValues, navController = navController, query = query)
        }
    }

}

@Composable
fun DashboardTopBar(focusManager: FocusManager, query: MutableState<String>) {
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
            SearchBar(focusManager = focusManager, query = query)
        }

    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    query:MutableState<String>,
    mainViewModel: MainViewModel= hiltViewModel()
) {
    TextField(
        value = query.value,
        onValueChange = {
            query.value = it
            mainViewModel.isSearchActive.value = query.value.isNotEmpty()
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.value.isNotEmpty()){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = modifier.clickable {
                        query.value = ""
                        focusManager.clearFocus()
                    }
                )
            }
        },
        maxLines = 1,
        keyboardActions = KeyboardActions(onSearch ={
            focusManager.clearFocus()
        }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
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
fun ListLeagues(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    query: MutableState<String>,
    mainViewModel: MainViewModel = hiltViewModel()) {

    when(val leagues = mainViewModel.leaguesState.collectAsState().value){
        is Resource.Success ->{
            Log.d("Dashboard", "success:$leagues")
            val leagueList = if (mainViewModel.isSearchActive.value){
                mainViewModel.filterLeagues(query = query.value,leagues.data!!)
            }else{
                leagues.data
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(leagueList!!.size) { position ->
                    ItemRow(leagueList[position].league) { selectedLeague ->
                        navController.navigate(Screen.LeagueInfoScreen.route + "/${selectedLeague.name}")
                    }
                }
            }
        }
        is Resource.Loading ->{
            Log.d("Dashboard", "Loading:${leagues.data}")
            Column(modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = modifier.size(40.dp),
                    color = MaterialTheme.colors.primary
                )
            }
        }
        else ->{
            Log.d("Dashboard", "Error:${leagues.data}")
            Column(modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = leagues.message!!)
                Spacer(modifier = modifier.height(10.dp))
                Button(onClick = { mainViewModel.getLeagues() }) {
                    Text(text = "Retry")
                }
            }
        }

    }



}

@Composable
fun ItemRow(league: League, selectedLeague: (League) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(end = 10.dp, bottom = 10.dp)
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .heightIn(160.dp)
                .clickable { selectedLeague(league) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberImagePainter(
                data = league.logo,
                builder = {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(vertical = 10.dp)
            )
            Text(
                text = league.name,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
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
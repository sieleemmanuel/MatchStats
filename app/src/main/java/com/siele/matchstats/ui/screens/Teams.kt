package com.siele.matchstats.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siele.matchstats.R
import com.siele.matchstats.ui.theme.MatchStatsTheme
import com.siele.matchstats.util.Constants.eplTeams

@Composable
fun TeamsTab() {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(eplTeams.size) { position ->
            TeamRow(eplTeams[position]) { clickedTeam ->
                Toast.makeText(context, clickedTeam, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun TeamRow(team: String, clickedTeam: (String) -> Unit) {
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
                    text = team,
                    modifier = Modifier.padding(5.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }

        }


}

@Preview(showSystemUi = true)
@Composable
fun TeamsPreview() {
    MatchStatsTheme {
        TeamsTab()
    }

}

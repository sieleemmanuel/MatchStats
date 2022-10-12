package com.siele.matchstats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siele.matchstats.R
import com.siele.matchstats.ui.theme.MatchStatsTheme

@Composable
fun StatsTab() {
Box(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier.padding(all = 16.dp)) {
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
        LazyColumn {
            items(count = 10){
                PlayerRow()
            }

        }

    }
}
}

@Composable
fun PlayerRow() {
    Box(modifier = Modifier.fillMaxWidth()) {

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
            Text(text = "1")
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id =  R.drawable.ic_launcher_background),
                contentDescription ="Player Profile",
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
                    Text(text = "Erling Haaland")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id =  R.drawable.ic_launcher_background),
                            contentDescription ="Player Profile",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Man City")
                    }

                }
                Text(text = "15")
            }

        }

    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun StatsPreview() {
    MatchStatsTheme {
        StatsTab()
    }

}


package com.siele.matchstats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siele.matchstats.ui.theme.MatchStatsTheme
import com.siele.matchstats.util.Constants

@Composable
fun StandingsTab() {
    val horizontalScrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier.fillMaxWidth() ){
            SeasonsDropdownMenu()
           Row (modifier = Modifier
               .horizontalScroll(horizontalScrollState)
           ){
               Text(
                   text = "Club",
                   modifier = Modifier
                       .width(150.dp)
                       .padding(start = 10.dp),
                   overflow = TextOverflow.Ellipsis,
                   maxLines = 1
               )
               Spacer(modifier = Modifier.width(10.dp))
               Text(text = "MP")
               Spacer(modifier = Modifier.width(30.dp))
               Text(text = "W")
               Spacer(modifier = Modifier.width(30.dp))
               Text(text = "D")
               Spacer(modifier = Modifier.width(30.dp))
               Text(text = "L")
               Spacer(modifier = Modifier.width(30.dp))
               Text(text = "Pts")
               Spacer(modifier = Modifier.width(30.dp))
               Text(text = "GF")
               Spacer(modifier = Modifier.width(30.dp))
               Text(text = "GA")
               Spacer(modifier = Modifier.width(30.dp))
               Text(text = "GD")
               Spacer(modifier = Modifier.width(30.dp))
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

@Preview(
    showSystemUi = true
)
@Composable
fun TablePreview() {
    MatchStatsTheme {
        StandingsTab()
    }
}
package com.example.dartmania.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.size.Size
import com.example.dartmania.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String?, showBackButton: Boolean = false, navController: NavController){
    CenterAlignedTopAppBar(
        title = { title?.let { Text(it) } },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        navigationIcon = {
            if (showBackButton){
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "arrowback"
                    )
                }
            }
        }
    )
}


@Composable
fun SimpleBottomAppBar(navController: NavController){
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationBar {
        NavigationBarItem(
            label = { Text("Home") },
            selected = currentScreen == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) },
            icon = { Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Go to home"
            )
            }
        )
    }
}

@Preview
@Composable
fun PointButtons(){

    Box(
        modifier = Modifier
            .fillMaxSize() // Make the Box take up the full available space
            //.padding(16.dp)
    ){
        Column (
            verticalArrangement = Arrangement.Bottom, // Align content to the bottom of the Column
            modifier = Modifier.fillMaxHeight()
        ){
            Row (
                modifier = Modifier.padding(top = 5.dp)
            ){
                CardsForPointButtons(title = "1")
                CardsForPointButtons(title = "2")
                CardsForPointButtons(title = "3")
                CardsForPointButtons(title = "4")
            }
            Row(
                modifier = Modifier.padding(top = 5.dp)
            ) {
                CardsForPointButtons(title = "5")
                CardsForPointButtons(title = "6")
                CardsForPointButtons(title = "7")
                CardsForPointButtons(title = "8")
            }
            Row(
                modifier = Modifier.padding(top = 5.dp)
            ){
                CardsForPointButtons(title = "9")
                CardsForPointButtons(title = "10")
                CardsForPointButtons(title = "11")
                CardsForPointButtons(title = "12")
            }
            Row(
                modifier = Modifier.padding(top = 5.dp)
            ){
                CardsForPointButtons(title = "13")
                CardsForPointButtons(title = "14")
                CardsForPointButtons(title = "15")
                CardsForPointButtons(title = "16")
            }
            Row(
                modifier = Modifier.padding(top = 5.dp)
            ){
                CardsForPointButtons(title = "17")
                CardsForPointButtons(title = "18")
                CardsForPointButtons(title = "19")
                CardsForPointButtons(title = "20")
            }
            Row(
                modifier = Modifier.padding(top = 5.dp, bottom = 8.dp)
            ){
                CardsForPointButtons(title = "25")
                CardsForPointButtons(title = "0", cardColor = MaterialTheme.colorScheme.error)
                CardsForPointButtons(title = "DOU", cardColor = MaterialTheme.colorScheme.tertiary)
                CardsForPointButtons(title = "TRI", cardColor = MaterialTheme.colorScheme.secondaryContainer)
            }
        }
    }
}

@Composable
fun CardsForPointButtons(title: String?, cardColor: Color = MaterialTheme.colorScheme.tertiaryContainer){
    Card (
        modifier = Modifier
            .size(width = 98.dp, height = 40.dp)
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ){
        title?.let {
            Text(
                text = it,
                fontSize = 26.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun PlayerStats(){
    Column {
        Row {
            Card(
                modifier = Modifier.size(width = 98.dp, height = 40.dp)
            ) {
                Text(text = "Player 1")
            }
            Column {

            }
            Column {

            }
        }
        Row {

        }
    }
}
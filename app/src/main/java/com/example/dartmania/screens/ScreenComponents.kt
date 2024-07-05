package com.example.dartmania.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dartmania.models.calculateCheckout
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

@Composable
fun PointButtons(onButtonClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxHeight()
        ) {
            val buttons = listOf(
                listOf("1", "2", "3", "4"),
                listOf("5", "6", "7", "8"),
                listOf("9", "10", "11", "12"),
                listOf("13", "14", "15", "16"),
                listOf("17", "18", "19", "20"),
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier.padding(top = 5.dp, bottom = 8.dp)
                ) {
                    row.forEach { title ->
                        CardsForPointButtons(title = title, onClick = onButtonClick)
                    }
                }
            }
            Row (
                modifier = Modifier.padding(top = 5.dp, bottom = 8.dp)
            ){
                CardsForPointButtons(title = "25", onClick = onButtonClick)

                CardsForPointButtons(
                    title = "DOU",
                    onClick = onButtonClick
                )
                CardsForPointButtons(
                    title = "TRI",
                    onClick = onButtonClick
                )
                CardsForPointButtons(
                    title = "0",
                    cardColor = MaterialTheme.colorScheme.error,
                    onClick = onButtonClick
                )
            }
        }
    }
}

@Composable
fun CardsForPointButtons(
    title: String?,
    isActive: Boolean = false,
    cardColor: Color = MaterialTheme.colorScheme.secondary,
    onClick: (String) -> Unit
) {
    Button(
        onClick = {
            title?.let {
                onClick(it)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) Color.Gray else cardColor
        ),
        modifier = Modifier
            .size(width = 98.dp, height =40.dp)
    ) {
        title?.let {
            Text(
                text = it,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PlayerStats(
    pointsRemain: Int = 501,
    average: Double = 0.0,
    shots: Int = 0,
    name: String = "Player"
){
    Column (
    ){
        Row {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .size(80.dp)
                    .padding(3.dp),
            ) {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween, // Space out the texts
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = name,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Text(
                        text = pointsRemain.toString(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                    )
                    Column {
                        Text(
                            text = String.format("%.2f", average),
                            fontSize = 25.sp,
                            modifier = Modifier
                                .padding(end = 5.dp)
                        )
                        Text(
                            text = shots.toString(),
                            fontSize = 25.sp
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun DartsPointsRow(
    visible: Boolean,
    darts: ArrayList<String>
) {
    if (visible) {
        Card(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "CPU throws: ",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 20.sp,
                    )
                darts.forEach { dart ->
                    Text(
                        dart + " |",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 20.sp,
                        //modifier = Modifier.padding(end = 5.dp)


                        )
                }
            }




        }
    }
}


//TODO: soll alle drei runden sich nur ändern und nicht nach jedem wurf
@Composable
fun ShowCheckout(
    pointsRemain: Int = 180,
    player: String = "default"
){
    if(pointsRemain <= 170 && (calculateCheckout(pointsRemain) != "No Checkout Possible" )) {
        Column (
        ){
            Card(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp), // Adjust height as needed
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = player + " " + calculateCheckout(remainingPoints = pointsRemain),
                        fontSize = 25.sp,
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }
}



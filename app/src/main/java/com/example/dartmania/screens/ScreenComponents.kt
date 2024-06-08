package com.example.dartmania.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Check
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.size.Size
import com.example.DartMania.R
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
fun CardsForPointButtons(title: String?, cardColor: Color = MaterialTheme.colorScheme.secondaryContainer, onClick: (String) -> Unit){
    Card (
        modifier = Modifier
            .size(width = 98.dp, height = 40.dp)
            .clickable {
                title?.let {
                    // Convert the title to an integer if possible, else handle special cases
                    val value = when (it) {
                        "DOU" -> 2  // times 2
                        "TRI" -> 3  // times 3
                        else -> it.toIntOrNull() ?: 0  // convert to int or default to 0
                    }
                    onClick(it)
                }
            },
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
fun PlayerStats(pointsRemain: Int = 501, average: Int = 0){
    Column {
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
                        text = "Player 1",
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
                    Text(
                        text = average.toString(),
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(end = 5.dp)
                        )
                }
            }
        }
    }
}


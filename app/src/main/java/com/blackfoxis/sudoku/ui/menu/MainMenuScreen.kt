package com.blackfoxis.sudoku.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blackfoxis.sudoku.SudokuGameSaver
import com.blackfoxis.sudoku.navigation.Screen

@Composable
fun MainMenuScreen(navController: NavController) {
    val context = LocalContext.current
    val hasSavedGame = remember { SudokuGameSaver.hasSavedGame(context) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Судоку", fontSize = 32.sp)

        Spacer(Modifier.height(32.dp))

        Button(onClick = {
            navController.navigate(Screen.Menu.route) // выбор сложности
        }) {
            Text("Новая игра")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(Screen.Game.route)
            },
            enabled = hasSavedGame
        ) {
            Text("Продолжить")
        }
    }
}

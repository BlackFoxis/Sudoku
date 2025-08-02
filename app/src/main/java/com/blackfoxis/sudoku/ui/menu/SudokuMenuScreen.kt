package com.blackfoxis.sudoku.ui.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blackfoxis.sudoku.SudokuGameHolder
import com.blackfoxis.sudoku.domain.SudokuGenerator
import com.blackfoxis.sudoku.model.Difficulty
import com.blackfoxis.sudoku.model.SudokuCell
import com.blackfoxis.sudoku.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun SudokuMenuScreen(navController: NavController) {

    val coroutineScope = rememberCoroutineScope()

    val difficulties = listOf(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD)
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Выбери сложность", fontSize = 24.sp)

        difficulties.forEach { difficulty ->
            Button(
                onClick = {
                    coroutineScope.launch {
                        // Генерация доски — фоновый поток
                        val (board, solution) = withContext(Dispatchers.Default) {
                            SudokuGenerator.generateWithSolution(difficulty)
                        }
                        // Сохраняем в "глобальное" хранилище
                        SudokuGameHolder.board = board
                        SudokuGameHolder.solution = solution
                        SudokuGameHolder.difficulty = difficulty

                        navController.navigate(Screen.Game.route)
                    }
                },
            ) {
                Text(difficulty.name)
            }
        }
    }
}
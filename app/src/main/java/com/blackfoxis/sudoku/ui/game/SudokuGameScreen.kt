package com.blackfoxis.sudoku.ui.game

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.blackfoxis.sudoku.SudokuGameHolder
import com.blackfoxis.sudoku.SudokuGameSaver
import com.blackfoxis.sudoku.model.Difficulty
import com.blackfoxis.sudoku.ui.board.SudokuBoard
import com.blackfoxis.sudoku.ui.keyboard.SudokuKeyboard


import com.blackfoxis.sudoku.viewmodel.SudokuViewModel
import kotlinx.coroutines.delay

@Composable
fun SudokuGameScreen() {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }



    val board = SudokuGameHolder.board
    val solution = SudokuGameHolder.solution
    val difficulty = SudokuGameHolder.difficulty

    LaunchedEffect(Unit) {
        delay(250)
        isLoading = false
    }

    if (board == null || solution == null || difficulty == null) {
        // fallback, если данные не переданы (например, прямой запуск)
        Text("Ошибка: нет данных", color = Color.Red)
        return
    }

    val viewModel = remember {
        SudokuViewModel(difficulty).apply {
            loadFrom(board, solution, difficulty)
        }
    }
    Crossfade(targetState = isLoading) { loading ->
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
                Text("Загружаем доску")
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(Modifier.padding(16.dp)) {
                    SudokuBoard(
                        board = viewModel.board,
                        selected = viewModel.selectedCell,
                        isHighlighted = viewModel::isHighlighted,
                        onCellClick = viewModel::onCellClick
                    )

                    Spacer(Modifier.height(16.dp))

                    SudokuKeyboard { number ->
                        viewModel.onInput(number)

                        //Сохранение
                        val boardData = viewModel.board.map { row -> row.map { it.value } }
                        val solutionData: List<List<Int>> = viewModel.solution.map { row ->
                            row.map { cell -> cell }
                        }

                        SudokuGameSaver.saveGame(
                            context = context,
                            board = boardData,
                            solution = solutionData,
                            difficulty = difficulty
                        )
                    }
                }
            }
        }
    }
}
package com.blackfoxis.sudoku.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackfoxis.sudoku.SudokuGameHolder.difficulty
import com.blackfoxis.sudoku.SudokuGameSaver
import com.blackfoxis.sudoku.model.Difficulty
import com.blackfoxis.sudoku.domain.SudokuGenerator
import com.blackfoxis.sudoku.model.SudokuCell
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SudokuViewModel(difficulty: Difficulty) : ViewModel() {

    var board = mutableStateListOf<SnapshotStateList<SudokuCell>>()
        private set

    var solution: Array<IntArray> = emptyArray()
        private set

    var selectedCell by mutableStateOf<SudokuCell?>(null)
        private set

    var hasWon by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(true)
        private set


    fun generate(difficulty: Difficulty) {
        viewModelScope.launch {
            isLoading = true

            val start = System.currentTimeMillis()

            val (b, s) = SudokuGenerator.generateWithSolution(difficulty)
            board.clear()
            board.addAll(b)
            solution = s

            // Подождать хотя бы 500 мс
            val elapsed = System.currentTimeMillis() - start
            val minDelay = 500L
            if (elapsed < minDelay) delay(minDelay - elapsed)

            isLoading = false
        }
    }
    fun loadFrom(
        boardData: List<List<SudokuCell>>,
        solutionData: Array<IntArray>,
        difficulty: Difficulty
    ) {
        board.clear()
        board.addAll(boardData.map { it.toMutableStateList() })
        solution = solutionData
        hasWon = false
        selectedCell = null
        isLoading = false
    }
    init {
//        val (b, s) = SudokuGenerator.generateWithSolution(difficulty)
//        board = b
//        solution = s
    }

    fun onCellClick(cell: SudokuCell) {
        selectedCell = cell
    }

    fun onInput(value: Int) {
        selectedCell?.let { cell ->
            if (!cell.isInitial && !cell.isCorrect) {
                val correct = solution[cell.row][cell.col]
                val updated = when {
                    value == 0 -> cell.copy(value = 0, isError = false)
                    value == correct -> cell.copy(value = value, isCorrect = true, isError = false)
                    else -> cell.copy(value = value, isError = true)
                }
                board[cell.row][cell.col] = updated
                selectedCell = updated


            }
        }
    }
    fun isHighlighted(cell: SudokuCell): Boolean {
        val selected = selectedCell ?: return false
        return cell.row == selected.row ||
                cell.col == selected.col ||
                (cell.row / 3 == selected.row / 3 && cell.col / 3 == selected.col / 3) ||
                (cell.value != 0 && cell.value == selected.value)
    }
}

package com.blackfoxis.sudoku.domain

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.blackfoxis.sudoku.model.Difficulty
import com.blackfoxis.sudoku.model.SudokuCell
import kotlin.random.Random



object SudokuGenerator {
    private const val SIZE = 9
    private const val EMPTY = 0

    fun generateWithSolution(difficulty: Difficulty)    : Pair<SnapshotStateList<SnapshotStateList<SudokuCell>>, Array<IntArray>> {
        val solution = Array(9) { IntArray(9) }
        val tempBoard = Array(9) { IntArray(9) }

        fillDiagonalBlocks(tempBoard)
        solveBoard(tempBoard)

        for (row in 0 until 9)
            for (col in 0 until 9)
                solution[row][col] = tempBoard[row][col]

        removeCells(tempBoard, difficulty.removedCells)

        val board = SnapshotStateList<SnapshotStateList<SudokuCell>>()
        for (row in 0 until 9) {
            val rowList = SnapshotStateList<SudokuCell>()
            for (col in 0 until 9) {
                val value = tempBoard[row][col]
                rowList.add(
                    SudokuCell(
                        row, col,
                        value = value,
                        isInitial = value != 0
                    )
                )
            }
            board.add(rowList)
        }

        return board to solution
    }
    fun generate(): SnapshotStateList<SnapshotStateList<SudokuCell>>{

        val board = SnapshotStateList<SnapshotStateList<SudokuCell>>()
        for (row in 0 until SIZE) {
            val rowList = SnapshotStateList<SudokuCell>()
            for (col in 0 until SIZE) {
                rowList.add(SudokuCell(row, col))
            }
            board.add(rowList)
        }


        val tempBoard = Array(SIZE) { IntArray(SIZE) { EMPTY } }
        fillDiagonalBlocks(tempBoard)
        solveBoard(tempBoard)
        removeCells(tempBoard, 40)


        for (row in 0 until SIZE) {
            for (col in 0 until SIZE) {
                val value = tempBoard[row][col]
                board[row][col] = SudokuCell(
                    row = row,
                    col = col,
                    value = value,
                    isInitial = value != EMPTY
                )
            }
        }
        return board
    } /* Главная функция генерации судоку-доски.  */

    private fun fillDiagonalBlocks(board: Array<IntArray>) {
        for (i in 0 until SIZE step 3) {
            fillBlock(board, i, i)
        }
    } /* Заполняет только диагональные 3x3 блоки (в начале, середине и конце) случайными числами от 1 до 9.
     Эти блоки не пересекаются, и их можно безопасно случайно заполнить, не нарушая правила. */

    private fun fillBlock(board: Array<IntArray>, row: Int, col: Int) {
        val nums = (1..9).shuffled()
        var idx = 0
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[row + i][col + j] = nums[idx++]
            }
        }
    } /* Заполняет один блок 3x3 случайными числами.
    Генерирует случайный список чисел 1–9 и вставляет его в блок 3x3 по заданным координатам.*/

    private fun isSafe(board: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        for (i in 0 until SIZE) {
            if (board[row][i] == num || board[i][col] == num) return false
        }
        val boxRow = row - row % 3
        val boxCol = col - col % 3
        for (i in 0 until 3)
            for (j in 0 until 3)
                if (board[boxRow + i][boxCol + j] == num) return false

        return true
    } /* Проверяет, можно ли вставить число num в ячейку [row][col], не нарушив правила судоку.*/

    private fun solveBoard(board: Array<IntArray>): Boolean {
        for (row in 0 until SIZE) {
            for (col in 0 until SIZE) {
                if (board[row][col] == EMPTY) {
                    for (num in 1..9) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num
                            if (solveBoard(board)) return true
                            board[row][col] = EMPTY
                        }
                    }
                    return false
                }
            }
        }
        return true
    } /* Решает судоку-доску (полностью заполняет её по правилам).
    Использует метод backtracking — перебирает возможные значения, пока не найдёт правильное. */

    private fun removeCells(board: Array<IntArray>, cellsToRemove: Int) {
        var removed = 0
        while (removed < cellsToRemove) {
            val row = Random.nextInt(SIZE)
            val col = Random.nextInt(SIZE)
            if (board[row][col] != EMPTY) {
                board[row][col] = EMPTY
                removed++
            }
        }
    } /* Удаляет заданное количество ячеек (заменяет их на 0, что означает «пусто»).
    Выбирает случайные координаты и удаляет значение, если оно ещё не пустое. Повторяет, пока не удалит нужное количество. */


}
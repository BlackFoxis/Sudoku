package com.blackfoxis.sudoku.model

data class SudokuCell(
    val row: Int,
    val col: Int,
    var value: Int = 0,
    val isInitial: Boolean = false,
    var isSelected: Boolean = false,
    var isCorrect: Boolean = false,
    var isError: Boolean = false
)
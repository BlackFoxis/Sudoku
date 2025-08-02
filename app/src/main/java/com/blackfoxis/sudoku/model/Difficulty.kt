package com.blackfoxis.sudoku.model

enum class Difficulty(val removedCells: Int) {
    EASY(30),
    MEDIUM(40),
    HARD(50)
}
package com.blackfoxis.sudoku

import com.blackfoxis.sudoku.model.Difficulty
import com.blackfoxis.sudoku.model.SudokuCell

object SudokuGameHolder {
    var board: List<List<SudokuCell>>? = null
    var solution: Array<IntArray>? = null
    var difficulty: Difficulty? = null
}
package com.blackfoxis.sudoku

import android.content.Context
import com.blackfoxis.sudoku.model.Difficulty

object SudokuGameSaver {
    private const val PREF_NAME = "sudoku_prefs"
    private const val BOARD_KEY = "board"
    private const val SOLUTION_KEY = "solution"
    private const val DIFFICULTY_KEY = "difficulty"

    fun saveGame(context: Context, board: List<List<Int>>, solution: List<List<Int>>, difficulty: Difficulty) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(BOARD_KEY, board.flatten().joinToString(","))
            putString(SOLUTION_KEY, solution.flatten().joinToString(","))
            putString(DIFFICULTY_KEY, difficulty.name)
            apply()
        }
    }

    fun loadGame(context: Context): Triple<List<List<Int>>, List<List<Int>>, Difficulty>? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val boardString = prefs.getString(BOARD_KEY, null) ?: return null
        val solutionString = prefs.getString(SOLUTION_KEY, null) ?: return null
        val difficultyName = prefs.getString(DIFFICULTY_KEY, null) ?: return null

        val board = boardString.split(",").map { it.toInt() }.chunked(9)
        val solution = solutionString.split(",").map { it.toInt() }.chunked(9)
        val difficulty = Difficulty.valueOf(difficultyName)

        return Triple(board, solution, difficulty)
    }


    fun hasSavedGame(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.contains(BOARD_KEY)
    }

    fun clear(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}

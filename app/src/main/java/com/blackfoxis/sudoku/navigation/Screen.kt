package com.blackfoxis.sudoku.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Menu : Screen("menu")
    object Game : Screen("game")
}
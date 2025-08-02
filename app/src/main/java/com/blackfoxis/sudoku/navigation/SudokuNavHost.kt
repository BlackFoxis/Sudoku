package com.blackfoxis.sudoku.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.blackfoxis.sudoku.ui.game.SudokuGameScreen
import com.blackfoxis.sudoku.ui.menu.MainMenuScreen
import com.blackfoxis.sudoku.ui.menu.SudokuMenuScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SudokuNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        composable(Screen.Menu.route) {
            SudokuMenuScreen(navController = navController)
        }

        composable(Screen.Game.route) {
            SudokuGameScreen()
        }
        composable(Screen.Main.route) {
            MainMenuScreen(navController)
        }
    }
}
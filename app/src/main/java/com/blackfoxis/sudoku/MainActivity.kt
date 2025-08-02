package com.blackfoxis.sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.blackfoxis.sudoku.navigation.SudokuNavHost
import com.blackfoxis.sudoku.ui.theme.SudokuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SudokuTheme {
                val navController = rememberNavController()
                SudokuNavHost(navController = navController)
            }
        }
    }
}
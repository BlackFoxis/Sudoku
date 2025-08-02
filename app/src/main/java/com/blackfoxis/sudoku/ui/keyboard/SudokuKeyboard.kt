package com.blackfoxis.sudoku.ui.keyboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuKeyboard(onKeyClick: (Int) -> Unit) {
    Column {
        for (row in listOf(listOf(1,2,3), listOf(4,5,6), listOf(7,8,9))) {
            Row {
                for (num in row) {
                    SudokuKeyButton(num.toString()) { onKeyClick(num) }
                }
            }
        }
        Row {
            SudokuKeyButton("C") { onKeyClick(0) }
        }
    }
}

@Composable
fun SudokuKeyButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp)
            .border(1.dp, Color.Black)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, fontSize = 20.sp)
    }
}
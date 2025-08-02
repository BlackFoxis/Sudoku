package com.blackfoxis.sudoku.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackfoxis.sudoku.model.SudokuCell

@Composable
fun SudokuCellView(
    cell: SudokuCell,
    isSelected: Boolean,
    highlight: Boolean,
    onClick: () -> Unit
) {
    val background = when {
        isSelected -> Color.Cyan //Выбранная ячейка
        cell.isCorrect -> Color(0xFFBBFFBB) //Правильный ответ
        cell.isError -> Color(0xFFFFCCCC) //Не правильный ответ
        highlight -> Color(0xFFE3F2FD) // Подсветка
        cell.isInitial -> Color.LightGray //Не изменяемые
        else -> Color.White //Пустые
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .padding(1.dp)
            .border(1.dp, Color.Gray)
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (cell.value == 0) "" else cell.value.toString(),
            fontSize = 18.sp
        )
    }
}
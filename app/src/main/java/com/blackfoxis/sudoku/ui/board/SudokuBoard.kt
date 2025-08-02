package com.blackfoxis.sudoku.ui.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.blackfoxis.sudoku.model.SudokuCell

@Composable
fun SudokuBoard(
    board: SnapshotStateList<SnapshotStateList<SudokuCell>>,
    selected: SudokuCell?,
    isHighlighted: (SudokuCell) -> Boolean,
    onCellClick: (SudokuCell) -> Unit
) {
    Column {
        for (row in board) {
            Row {
                for (cell in row) {
                    SudokuCellView(
                        cell = cell,
                        isSelected = cell == selected,
                        highlight = isHighlighted(cell),
                        onClick = { onCellClick(cell) }
                    )
                }
            }
        }
    }
}
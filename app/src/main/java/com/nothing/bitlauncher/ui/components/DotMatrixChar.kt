package com.nothing.bitlauncher.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nothing.bitlauncher.ui.theme.NothingPureWhite

object DotFont {
    // 5 kolom x 7 baris untuk representasi matriks dot ala Nothing OS
    val DIGITS: Map<Char, List<IntArray>> = mapOf(
        '0' to listOf(
            intArrayOf(0, 1, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 1, 1),
            intArrayOf(1, 0, 1, 0, 1),
            intArrayOf(1, 1, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0)
        ),
        '1' to listOf(
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 1, 1, 0, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 1, 1, 1, 0)
        ),
        '2' to listOf(
            intArrayOf(0, 1, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(0, 0, 0, 1, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 1, 0, 0, 0),
            intArrayOf(1, 1, 1, 1, 1)
        ),
        '3' to listOf(
            intArrayOf(1, 1, 1, 1, 0),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0)
        ),
        '4' to listOf(
            intArrayOf(0, 0, 0, 1, 0),
            intArrayOf(0, 0, 1, 1, 0),
            intArrayOf(0, 1, 0, 1, 0),
            intArrayOf(1, 0, 0, 1, 0),
            intArrayOf(1, 1, 1, 1, 1),
            intArrayOf(0, 0, 0, 1, 0),
            intArrayOf(0, 0, 0, 1, 0)
        ),
        '5' to listOf(
            intArrayOf(1, 1, 1, 1, 1),
            intArrayOf(1, 0, 0, 0, 0),
            intArrayOf(1, 1, 1, 1, 0),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0)
        ),
        '6' to listOf(
            intArrayOf(0, 1, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 0),
            intArrayOf(1, 0, 0, 0, 0),
            intArrayOf(1, 1, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0)
        ),
        '7' to listOf(
            intArrayOf(1, 1, 1, 1, 1),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(0, 0, 0, 1, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 1, 0, 0, 0),
            intArrayOf(0, 1, 0, 0, 0),
            intArrayOf(0, 1, 0, 0, 0)
        ),
        '8' to listOf(
            intArrayOf(0, 1, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0)
        ),
        '9' to listOf(
            intArrayOf(0, 1, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 1),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(0, 0, 0, 0, 1),
            intArrayOf(0, 1, 1, 1, 0)
        ),
        ':' to listOf(
            intArrayOf(0),
            intArrayOf(0),
            intArrayOf(1),
            intArrayOf(0),
            intArrayOf(1),
            intArrayOf(0),
            intArrayOf(0)
        )
    )
}

@Composable
fun DotMatrixChar(
    char: Char,
    modifier: Modifier = Modifier,
    dotRadius: Dp = 3.2.dp,
    dotSpacing: Dp = 2.0.dp,
    activeColor: Color = NothingPureWhite,
    inactiveColor: Color = Color.Transparent
) {
    val matrix = DotFont.DIGITS[char] ?: return
    val rows = matrix.size
    val cols = matrix[0].size

    val dotDiameter = dotRadius * 2
    val totalWidth = (dotDiameter * cols) + (dotSpacing * (cols - 1))
    val totalHeight = (dotDiameter * rows) + (dotSpacing * (rows - 1))

    Canvas(
        modifier = modifier.size(width = totalWidth, height = totalHeight)
    ) {
        val radiusPx = dotRadius.toPx()
        val spacingPx = dotSpacing.toPx()
        val diameterPx = radiusPx * 2

        for (r in 0 until rows) {
            val y = r * (diameterPx + spacingPx) + radiusPx
            for (c in 0 until cols) {
                val x = c * (diameterPx + spacingPx) + radiusPx
                val isLit = matrix[r][c] == 1
                if (isLit) {
                    drawCircle(
                        color = activeColor,
                        radius = radiusPx,
                        center = Offset(x, y)
                    )
                } else if (inactiveColor != Color.Transparent) {
                    drawCircle(
                        color = inactiveColor,
                        radius = radiusPx,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

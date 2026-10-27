package com.nothing.bitlauncher.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nothing.bitlauncher.ui.theme.NothingGrayText
import com.nothing.bitlauncher.ui.theme.NothingPureWhite
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DotMatrixClockWidget(
    modifier: Modifier = Modifier,
    onClockClick: () -> Unit = {},
    onDateClick: () -> Unit = {}
) {
    var currentTime by remember { mutableStateOf(Calendar.getInstance().time) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance().time
            delay(1000L)
        }
    }

    // Format jam tanpa leading 0 jika jam 1-9 (contoh "2:36" persis seperti di gambar)
    val timeFormat = remember { SimpleDateFormat("h:mm", Locale.getDefault()) }
    val timeString = timeFormat.format(currentTime)

    // Format tanggal "TUE 27 OCT"
    val dateFormat = remember { SimpleDateFormat("EEE d MMM", Locale.US) }
    val dateText = dateFormat.format(currentTime).uppercase()

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dot Matrix Digital Clock (HH:MM or H:MM)
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onClockClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (char in timeString) {
                if (char == ':') {
                    DotMatrixChar(
                        char = ':',
                        dotRadius = 3.2.dp,
                        dotSpacing = 2.0.dp,
                        activeColor = NothingPureWhite
                    )
                } else if (char.isDigit()) {
                    DotMatrixChar(
                        char = char,
                        dotRadius = 3.2.dp,
                        dotSpacing = 2.0.dp,
                        activeColor = NothingPureWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Date Display: "TUE 27 OCT"
        Text(
            text = dateText,
            color = NothingGrayText,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            letterSpacing = 3.5.sp,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onDateClick() }
        )
    }
}

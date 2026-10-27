package com.nothing.bitlauncher.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.nothing.bitlauncher.model.AppInfo
import com.nothing.bitlauncher.ui.theme.*

private val monochromeMatrix = ColorMatrix().apply { setToSaturation(0f) }
private val monochromeFilter = ColorFilter.colorMatrix(monochromeMatrix)

@Composable
fun AppGridItem(
    app: AppInfo,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    useMonochromeIcon: Boolean = false,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            }
            .padding(vertical = 6.dp, horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Lingkaran icon
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(Color(0x18FFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberDrawablePainter(drawable = app.icon),
                contentDescription = app.label,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                colorFilter = if (useMonochromeIcon) monochromeFilter else null
            )
        }

        if (showLabel) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = app.label,
                color = NothingWhite,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AppListItem(
    app: AppInfo,
    modifier: Modifier = Modifier,
    useMonochromeIcon: Boolean = false,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            }
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0x18FFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberDrawablePainter(drawable = app.icon),
                contentDescription = app.label,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape),
                colorFilter = if (useMonochromeIcon) monochromeFilter else null
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = app.label,
            color = NothingWhite,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

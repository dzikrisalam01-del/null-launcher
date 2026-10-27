package com.nothing.bitlauncher.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nothing.bitlauncher.model.AppInfo
import com.nothing.bitlauncher.ui.components.AppGridItem
import com.nothing.bitlauncher.ui.components.DotMatrixClockWidget
import com.nothing.bitlauncher.ui.components.NothingSearchBar
import com.nothing.bitlauncher.ui.theme.*

@Composable
fun HomeScreen(
    homeGridApps: List<AppInfo>,
    dockApps: List<AppInfo>,
    useMonochrome: Boolean,
    onAppClick: (AppInfo) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenClock: () -> Unit,
    onOpenCalendar: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07090C))
            // Pattern Dot Matrix halus khas Nothing OS di seluruh layar
            .drawBehind {
                val dotColor = Color(0x18FFFFFF)
                val dotRadius = 1.0.dp.toPx()
                val spacing = 22.dp.toPx()

                var x = spacing / 2
                while (x < size.width) {
                    var y = spacing / 2
                    while (y < size.height) {
                        drawCircle(color = dotColor, radius = dotRadius, center = Offset(x, y))
                        y += spacing
                    }
                    x += spacing
                }
            }
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // TOP BAR: ":: NULL" di kiri dan icon slider pengaturan di kanan
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: 6-dot matrix icon + "NULL"
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 6 Dots (2 kolom x 3 baris)
                    Canvas(modifier = Modifier.size(width = 10.dp, height = 14.dp)) {
                        val dotR = 1.2.dp.toPx()
                        val colSpacing = 6.dp.toPx()
                        val rowSpacing = 5.dp.toPx()
                        for (col in 0..1) {
                            for (row in 0..2) {
                                drawCircle(
                                    color = NothingWhite,
                                    radius = dotR,
                                    center = Offset(col * colSpacing + dotR, row * rowSpacing + dotR)
                                )
                            }
                        }
                    }

                    Text(
                        text = "NULL",
                        color = NothingWhite,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        letterSpacing = 4.sp
                    )
                }

                // Right: Settings slider icon
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Pengaturan",
                    tint = NothingWhite,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onOpenSettings() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // WIDGET JAM DOT-MATRIX & TANGGAL
            DotMatrixClockWidget(
                onClockClick = onOpenClock,
                onDateClick = onOpenCalendar
            )

            Spacer(modifier = Modifier.height(14.dp))

            // SEARCH BAR "Cari aplikasi" dengan dot merah Nothing
            NothingSearchBar(
                query = "",
                onQueryChange = {},
                placeholder = "Cari aplikasi",
                onClick = onOpenSearch
            )

            Spacer(modifier = Modifier.height(26.dp))

            // HOME APP GRID (4 Kolom x 2 Baris = 8 Aplikasi)
            if (homeGridApps.isNotEmpty()) {
                val displayGridApps = homeGridApps.take(8)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    displayGridApps.chunked(4).forEach { rowApps ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowApps.forEach { app ->
                                AppGridItem(
                                    app = app,
                                    useMonochromeIcon = useMonochrome,
                                    showLabel = true,
                                    onClick = { onAppClick(app) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            // Fill remaining spaces in row if less than 4
                            for (i in 0 until (4 - rowApps.size)) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // DIVIDER HALUS DI ATAS DOCK
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                thickness = 0.5.dp,
                color = Color(0x22FFFFFF)
            )

            // BOTTOM DOCK (4 Pinned Icons tanpa label teks)
            if (dockApps.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dockApps.take(4).forEach { app ->
                        AppGridItem(
                            app = app,
                            useMonochromeIcon = useMonochrome,
                            showLabel = false,
                            onClick = { onAppClick(app) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

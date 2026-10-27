package com.nothing.bitlauncher.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nothing.bitlauncher.model.AppInfo
import com.nothing.bitlauncher.ui.components.AppGridItem
import com.nothing.bitlauncher.ui.components.AppListItem
import com.nothing.bitlauncher.ui.components.NothingSearchBar
import com.nothing.bitlauncher.ui.theme.*

@Composable
fun AppDrawerSheet(
    allApps: List<AppInfo>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    useMonochrome: Boolean,
    onToggleMonochrome: () -> Unit,
    onAppClick: (AppInfo) -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isGridView by remember { mutableStateOf(true) }

    val filteredApps = remember(allApps, searchQuery) {
        if (searchQuery.isBlank()) {
            allApps.sortedBy { it.label.lowercase() }
        } else {
            allApps.filter {
                it.label.contains(searchQuery.trim(), ignoreCase = true) ||
                        it.packageName.contains(searchQuery.trim(), ignoreCase = true)
            }.sortedBy { it.label.lowercase() }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07090C))
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
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tombol Tutup / Kembali
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onCloseDrawer() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = NothingWhite,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SEMUA APLIKASI",
                        color = NothingWhite,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        letterSpacing = 2.sp
                    )
                }

                // Controls (Grid/List & Monochrome)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(
                        imageVector = if (isGridView) Icons.Default.List else Icons.Default.GridView,
                        contentDescription = "Toggle View",
                        tint = NothingGrayText,
                        modifier = Modifier
                            .size(19.dp)
                            .clickable { isGridView = !isGridView }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search bar
            NothingSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Cari aplikasi...",
                onSearchSubmit = {
                    if (filteredApps.isNotEmpty()) {
                        onAppClick(filteredApps.first())
                    }
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // App list / grid
            if (filteredApps.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Aplikasi tidak ditemukan",
                        color = NothingGrayText,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                }
            } else if (isGridView) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filteredApps, key = { it.packageName }) { app ->
                        AppGridItem(
                            app = app,
                            useMonochromeIcon = useMonochrome,
                            showLabel = true,
                            onClick = { onAppClick(app) }
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filteredApps, key = { it.packageName }) { app ->
                        AppListItem(
                            app = app,
                            useMonochromeIcon = useMonochrome,
                            onClick = { onAppClick(app) }
                        )
                    }
                }
            }
        }
    }
}

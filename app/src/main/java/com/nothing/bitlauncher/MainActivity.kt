package com.nothing.bitlauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.nothing.bitlauncher.model.AppInfo
import com.nothing.bitlauncher.ui.screens.AppDrawerSheet
import com.nothing.bitlauncher.ui.screens.HomeScreen
import com.nothing.bitlauncher.ui.theme.NothingBitLauncherTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aktifkan Edge-to-Edge display untuk Samsung Galaxy S24 (AMOLED 120Hz)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NothingBitLauncherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF07090C)
                ) {
                    var installedApps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }
                    var isDrawerOpen by remember { mutableStateOf(false) }
                    var searchQuery by remember { mutableStateOf("") }
                    var useMonochrome by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        installedApps = loadInstalledApps()
                    }

                    // 8 Aplikasi Layar Beranda (Phone, Messages, Chrome, Photos, Clock, Settings, Calendar, Camera)
                    val homeGridApps = remember(installedApps) {
                        findHomeApps(installedApps)
                    }

                    // 4 Aplikasi Dock Bawah (Phone, Messages, Chrome, Photos)
                    val dockApps = remember(homeGridApps, installedApps) {
                        if (homeGridApps.size >= 4) homeGridApps.take(4) else installedApps.take(4)
                    }

                    // Penanganan tombol Back Android
                    BackHandler(enabled = true) {
                        if (searchQuery.isNotEmpty()) {
                            searchQuery = ""
                        } else if (isDrawerOpen) {
                            isDrawerOpen = false
                        }
                    }

                    AnimatedContent(
                        targetState = isDrawerOpen,
                        transitionSpec = {
                            if (targetState) {
                                (slideInVertically { it } + fadeIn()).togetherWith(
                                    slideOutVertically { -it / 3 } + fadeOut()
                                )
                            } else {
                                (slideInVertically { -it / 3 } + fadeIn()).togetherWith(
                                    slideOutVertically { it } + fadeOut()
                                )
                            }
                        },
                        label = "DrawerTransition"
                    ) { drawerState ->
                        if (drawerState) {
                            AppDrawerSheet(
                                allApps = installedApps,
                                searchQuery = searchQuery,
                                onSearchQueryChange = { searchQuery = it },
                                useMonochrome = useMonochrome,
                                onToggleMonochrome = { useMonochrome = !useMonochrome },
                                onAppClick = { app ->
                                    launchApp(app)
                                    isDrawerOpen = false
                                    searchQuery = ""
                                },
                                onCloseDrawer = {
                                    searchQuery = ""
                                    isDrawerOpen = false
                                }
                            )
                        } else {
                            HomeScreen(
                                homeGridApps = homeGridApps,
                                dockApps = dockApps,
                                useMonochrome = useMonochrome,
                                onAppClick = { app -> launchApp(app) },
                                onOpenSearch = { isDrawerOpen = true },
                                onOpenClock = { openClockApp() },
                                onOpenCalendar = { openCalendarApp() },
                                onOpenSettings = { openHomeSettings() }
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadInstalledApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val pm = packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(
                mainIntent,
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            pm.queryIntentActivities(mainIntent, 0)
        }

        activities
            .filter { it.activityInfo.packageName != packageName }
            .map { resolveInfo ->
                AppInfo(
                    label = resolveInfo.loadLabel(pm).toString(),
                    packageName = resolveInfo.activityInfo.packageName,
                    icon = resolveInfo.loadIcon(pm)
                )
            }
            .sortedBy { it.label.lowercase() }
    }

    /**
     * Menemukan 8 aplikasi utama persis seperti di gambar tampilan S24:
     * 1. Phone, 2. Messages, 3. Chrome, 4. Photos,
     * 5. Clock, 6. Settings, 7. Calendar, 8. Camera
     */
    private fun findHomeApps(apps: List<AppInfo>): List<AppInfo> {
        if (apps.isEmpty()) return emptyList()

        val targetCategories = listOf(
            listOf("dialer", "phone", "telepon", "kontak"),
            listOf("messaging", "message", "sms", "pesan"),
            listOf("chrome", "browser", "internet"),
            listOf("photos", "gallery", "galeri", "foto"),
            listOf("deskclock", "clock", "jam"),
            listOf("settings", "pengaturan", "setelan"),
            listOf("calendar", "kalender"),
            listOf("camera", "kamera")
        )

        val selected = mutableListOf<AppInfo>()

        for (keywords in targetCategories) {
            val match = apps.firstOrNull { app ->
                !selected.contains(app) && keywords.any { kw ->
                    app.packageName.contains(kw, ignoreCase = true) ||
                            app.label.contains(kw, ignoreCase = true)
                }
            }
            if (match != null) {
                selected.add(match)
            }
        }

        // Jika ada yang kurang dari 8, lengkapi dengan aplikasi lain
        for (app in apps) {
            if (selected.size >= 8) break
            if (!selected.contains(app)) {
                selected.add(app)
            }
        }

        return selected
    }

    private fun launchApp(app: AppInfo) {
        val launchIntent = packageManager.getLaunchIntentForPackage(app.packageName)
        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            Toast.makeText(this, "Tidak dapat membuka ${app.label}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openClockApp() {
        try {
            val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
            startActivity(intent)
        } catch (e: Exception) {
            val deskClock = packageManager.getLaunchIntentForPackage("com.google.android.deskclock")
                ?: packageManager.getLaunchIntentForPackage("com.sec.android.app.clockpackage")
            if (deskClock != null) startActivity(deskClock)
        }
    }

    private fun openCalendarApp() {
        try {
            val builder = CalendarContract.CONTENT_URI.buildUpon().appendPath("time")
            val intent = Intent(Intent.ACTION_VIEW).setData(builder.build())
            startActivity(intent)
        } catch (e: Exception) {
            val calIntent = packageManager.getLaunchIntentForPackage("com.google.android.calendar")
                ?: packageManager.getLaunchIntentForPackage("com.samsung.android.calendar")
            if (calIntent != null) startActivity(calIntent)
        }
    }

    private fun openHomeSettings() {
        try {
            val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            } else {
                Intent(Settings.ACTION_SETTINGS)
            }
            startActivity(intent)
        } catch (e: Exception) {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }
}

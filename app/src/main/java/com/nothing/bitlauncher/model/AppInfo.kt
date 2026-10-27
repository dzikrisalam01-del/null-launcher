package com.nothing.bitlauncher.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val label: String,
    val packageName: String,
    val icon: Drawable,
    val isFavorite: Boolean = false
)

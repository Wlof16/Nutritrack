package com.fit2081.XuanRen_33523436.nutritrack.data.theme

import android.content.Context
import androidx.core.content.edit
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager

class ThemeRepository(context: Context) {
    private val pref = context.getSharedPreferences(AuthManager.getStudentId().toString(), Context.MODE_PRIVATE)

    fun saveDarkMode(isDark: Boolean) {
        pref.edit() { putBoolean("dark_theme", isDark) }
    }

    fun getDarkMode(): Boolean {
        return pref.getBoolean("dark_theme", false)
    }
}
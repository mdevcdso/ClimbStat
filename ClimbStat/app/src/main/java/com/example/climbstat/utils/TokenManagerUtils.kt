// app/src/main/java/com/example/climbstat/utils/AuthTokenUtil.kt
package com.example.climbstat.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenManagerUtils(context: Context) {
    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_TOKEN = "auth_token"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun hasToken(): Boolean {
        val token = getToken()
        return !token.isNullOrBlank()
    }

    fun clearToken() {
        prefs.edit { remove(KEY_TOKEN) }
    }
}
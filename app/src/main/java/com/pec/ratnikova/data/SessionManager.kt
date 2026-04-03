package com.pec.ratnikova.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("student_session", Context.MODE_PRIVATE)

    fun saveStudentCode(code: String) {
        prefs.edit().putString("student_code", code).apply()
    }

    fun getStudentCode(): String? {
        return prefs.getString("student_code", null)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    fun isAutoLoginEnabled(): Boolean {
        return prefs.getBoolean("auto_login", false)
    }

    fun setAutoLogin(enabled: Boolean) {
        prefs.edit().putBoolean("auto_login", enabled).apply()
    }
}

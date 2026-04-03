package com.pec.ratnikova.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("student_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_STUDENT_CODE = "student_code"
    }

    fun saveStudentCode(code: String) {
        prefs.edit().putString(KEY_STUDENT_CODE, code).apply()
    }

    fun getStudentCode(): String? {
        return prefs.getString(KEY_STUDENT_CODE, null)
    }

    fun clearStudentCode() {
        prefs.edit().remove(KEY_STUDENT_CODE).apply()
    }
}

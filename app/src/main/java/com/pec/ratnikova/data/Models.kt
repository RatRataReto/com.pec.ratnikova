package com.pec.ratnikova.data

import com.google.gson.annotations.SerializedName

data class Student(
    val id: String,
    @SerializedName("full_name") val fullName: String,
    val organization: String,
    @SerializedName("issue_date") val issue_date: String,
    val specialty: String,
    val course: String,
    val hash: String?,
    @SerializedName("avatar_base64") val avatarBase64: String?
) {
    fun getAvatarBytes(): ByteArray? {
        if (avatarBase64.isNullOrBlank() || !avatarBase64.contains("base64,")) return null
        return try {
            android.util.Base64.decode(avatarBase64.substringAfter("base64,"), android.util.Base64.NO_WRAP)
        } catch (e: Exception) {
            null
        }
    }
}

data class LoginRequest(
    val code: String,
    val mood: String = "neutral"
)

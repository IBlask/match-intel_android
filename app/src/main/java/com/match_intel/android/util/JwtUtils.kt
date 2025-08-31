package com.match_intel.android.util

import android.content.Context
import android.util.Base64
import org.json.JSONObject

fun getUsernameFromJwt(context: Context): String? {
    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    val jwt = prefs.getString("jwt_token", null) ?: return null
    val parts = jwt.split(".")
    if (parts.size < 2) return null
    val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
    return JSONObject(payload).optString("sub", "")
}

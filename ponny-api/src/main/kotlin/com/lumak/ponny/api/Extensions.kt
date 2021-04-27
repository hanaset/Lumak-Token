package com.lumak.ponny.api

import com.google.gson.Gson

fun Any.toJson(): String {
    return Gson().toJson(this)
}
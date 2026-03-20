package com.vegardandersen.ikt205g26v_01_android.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(val title: String, val description: String)
package com.vikayarska.data.model

import java.io.Serializable

data class AppUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val intro: String,
    val imageUrl: String
) : Serializable

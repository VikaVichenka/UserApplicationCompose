package com.vikayarska.kotlinapplicationcompose.domain.model

import androidx.room.*
import java.util.*

@Entity(
    tableName = "users", indices = [Index(
        value = ["first_name", "last_name"],
        unique = true
    )]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val birth: Date,
    var intro: String?
)

data class UserMinimal(
    val id: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String
)

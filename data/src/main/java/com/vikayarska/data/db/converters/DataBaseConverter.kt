package com.vikayarska.data.db.converters

import androidx.room.TypeConverter
import java.util.*

class DataBaseConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }

}
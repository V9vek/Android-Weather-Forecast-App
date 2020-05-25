package com.viveksharma.forecastmvvm.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.viveksharma.forecastmvvm.data.database.entity.Day

@Entity(tableName = "future_weather_table", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(              //indices on date going to fasten the query based on date and no two rows can have same date
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val date: String,

    @Embedded
    val day: Day
)
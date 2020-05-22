package com.viveksharma.forecastmvvm.data.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location_table")
data class WeatherLocation(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("tz_id")
    val tzId: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID

    //Adjusting time with the location(as location changes, time changes, so we get correct weather)
    val zonedDateTime: ZonedDateTime
    get() {
        val instant = Instant.ofEpochSecond(localtimeEpoch)
        val zoneId = ZoneId.of(tzId)
        return ZonedDateTime.ofInstant(instant, zoneId)
    }
}
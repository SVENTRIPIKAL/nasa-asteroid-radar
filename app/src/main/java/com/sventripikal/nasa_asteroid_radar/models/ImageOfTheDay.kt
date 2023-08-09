package com.sventripikal.nasa_asteroid_radar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity("imageTable")
data class ImageOfTheDay(
    @PrimaryKey @ColumnInfo("url") var url: String,
    @ColumnInfo("media_Type") var mediaType: String,
    @ColumnInfo("title") var title: String,
    @ColumnInfo("date") var date: String
)
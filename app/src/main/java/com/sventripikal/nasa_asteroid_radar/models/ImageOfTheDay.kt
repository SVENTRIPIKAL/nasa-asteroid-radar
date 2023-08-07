package com.sventripikal.nasa_asteroid_radar.models

import okhttp3.MediaType



data class ImageOfTheDay(
    var url: String,
    var mediaType: MediaType,
    var title: String
)
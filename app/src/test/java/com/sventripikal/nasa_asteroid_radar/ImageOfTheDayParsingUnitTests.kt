package com.sventripikal.nasa_asteroid_radar

import com.sventripikal.nasa_asteroid_radar.models.ImageOfTheDay
import com.sventripikal.nasa_asteroid_radar.utils.fake_data.fakeImageOfTheDayJson
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import org.junit.Assert.assertEquals
import org.junit.Test

private val json = Json { ignoreUnknownKeys = true }

private fun extractImage(query: String): ImageOfTheDay? {

    val obj = json.decodeFromString<JsonObject>(query)


    val mediaType = json.decodeFromJsonElement<String>( obj["media_type"]!! )

    return if (mediaType == "image") {

        val date = json.decodeFromJsonElement<String>( obj["date"]!! )
        val title = json.decodeFromJsonElement<String>( obj["title"]!! )
        val url = json.decodeFromJsonElement<String>( obj["url"]!! )

        ImageOfTheDay(
            date = date,
            mediaType = mediaType,
            title = title,
            url = url
        )

    } else null
}


class ImageOfTheDayParsingUnitTests {

    @Test
    fun when_responseReceived_should_convertToImageOfTheDay() {

        val expected = ImageOfTheDay(
            "https://apod.nasa.gov/apod/image/2308/Perseids18_Horalek_960.jpg",
            "image",
            "Meteor Shower: Perseids from Perseus",
            "2023-08-09"
        )
        val actual = extractImage(fakeImageOfTheDayJson)

        println("EXPECTED: $expected      ACTUAL: $actual")

        assertEquals(expected, actual)
    }
}
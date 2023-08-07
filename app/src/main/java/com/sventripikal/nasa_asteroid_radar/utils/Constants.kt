package com.sventripikal.nasa_asteroid_radar.utils

import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



// json query constants
const val JSON_NEAR_EARTH_OBJECTS = "near_earth_objects"
const val JSON_ID = "id"
const val JSON_NAME = "name"
const val JSON_ABSOLUTE_MAGNITUDE_H = "absolute_magnitude_h"
const val JSON_ESTIMATED_DIAMETER = "estimated_diameter"
const val JSON_KILOMETERS = "kilometers"
const val JSON_ESTIMATED_DIAMETER_MAX = "estimated_diameter_max"
const val JSON_IS_POTENTIALLY_HAZARDOUS = "is_potentially_hazardous_asteroid"
const val JSON_APPROACH_DATA = "close_approach_data"
const val JSON_RELATIVE_VELOCITY = "relative_velocity"
const val JSON_KILOMETERS_PER_SECOND = "kilometers_per_second"
const val JSON_CLOSE_APPROACH_DATE = "close_approach_date"
const val JSON_MISS_DISTANCE = "miss_distance"
const val JSON_ASTRONOMICAL = "astronomical"



// current date format pattern
private const val DATE_PATTERN_STRING = "yyyy-MM-dd"

// returns current date as formatted string
fun getCurrentDateString(): String {

    // create calendar instance
    val calendar = Calendar.getInstance()

    // create formatter with date string pattern
    val formatter = SimpleDateFormat(DATE_PATTERN_STRING, Locale.getDefault())

    // return formatted date as string
    return formatter.format(calendar.time).toString()
}


// logging tag
const val TAG = "_SVENTRIPIKAL"

// logging message
const val MESSAGE_CREATE = "[ON-CREATE]"
const val MESSAGE_START = "[ON-START]"
const val MESSAGE_RESUME = "[ON-RESUME]"
const val MESSAGE_PAUSE = "[ON-PAUSE]"
const val MESSAGE_STOP = "[ON-STOP]"
const val MESSAGE_DESTROY = "[ON-DESTROY]"

// logging priority enum
enum class Priority { ERROR, VERBOSE, DEBUG, INFO }

// timber logging function
fun timber(tag: String, message: String, priority: Priority) {

    when (priority) {
        Priority.ERROR -> Timber.tag(tag).e(message)
        Priority.VERBOSE -> Timber.tag(tag).v(message)
        Priority.DEBUG -> Timber.tag(tag).d(message)
        Priority.INFO -> Timber.tag(tag).i(message)
    }
}



// Json config
private val json = Json { ignoreUnknownKeys = true }

// returns JsonObject of near_earth_objects
private fun getNearEarthObjects(query: String): JsonObject {

    // convert string query to JsonObject
    val jsonObject = json.decodeFromString<JsonObject>(query)

    // extract near_earth_objects element from JsonObject
    val element = jsonObject[JSON_NEAR_EARTH_OBJECTS]!!

    // convert JsonElement back to JsonObject and return
    return json.decodeFromJsonElement(element)
}

// returns Map<K, V> of String Dates & JsonArray of JsonObjects
private fun getMapOfStringDatesAndJsonArrays(obj: JsonObject):  Map<String, JsonArray> {

    // create new Map to hold StringDates and JsonArrays
    val map: MutableMap<String, JsonArray> = mutableMapOf()

    // for each String key in object
    obj.keys.forEach { stringDate ->

        // extract JsonElement from key string
        val element = obj[stringDate]

        // convert JsonElement to JsonArray
        val array = json.decodeFromJsonElement<JsonArray>(element!!)

        // map the JsonArray to String Date
        map[stringDate] = array
    }

    // return map
    return  map
}


// returns Map<K, V> of String Dates & List of Asteroids
private fun getAsteroidsFromJsonArrays(map: Map<String, JsonArray>): Map<String, List<Asteroid>> {

    // create map
    val mapOfAsteroids = mutableMapOf<String, List<Asteroid>>()

    // for each key Date String
    map.keys.forEach { dateString ->

        // get JsonArray from key String
        val jArray1 = map[dateString]!!

        // create list for Asteroids
        val asteroidList = mutableListOf<Asteroid>()


        // for each element in JsonArray
        jArray1.forEach { element ->

            // convert the JsonElement to a JsonObject
            val obj = json.decodeFromJsonElement<JsonObject>(element)

            /**
             * ASTEROID(
             *      id: String [x]
             *      name: String [x]
             *      absoluteMagnitude: Double [x]
             *      estimatedDiameterMax: Double [x]
             *      isPotentiallyHazardousAsteroid: Boolean [x]
             *      closeApproachDate: String [x]
             *      kilometersPerSecond: String [x]
             *      astronomical: String [x]
             *  )
             */

            // extract ID from object, decode, and assign
            val id: String = json.decodeFromJsonElement( obj[JSON_ID]!! )


            // extract NAME from object, decode, and assign
            val name: String = json.decodeFromJsonElement( obj[JSON_NAME]!! )


            // extract MAGNITUDE from object, decode, and assign
            val absoluteMagnitude: Double = json.decodeFromJsonElement( obj[JSON_ABSOLUTE_MAGNITUDE_H]!! )


            // extract DIAMETER from object, decode, and assign
            val kElm = obj[JSON_ESTIMATED_DIAMETER]!!.jsonObject[JSON_KILOMETERS]!!
            val kObj = json.decodeFromJsonElement<JsonObject>(kElm)
            val estimatedDiameterMax: Double =
                json.decodeFromJsonElement(kObj[JSON_ESTIMATED_DIAMETER_MAX]!!)


            // extract HAZARD from object, decode, and assign
            val isPotentiallyHazardousAsteroid: Boolean =
                json.decodeFromJsonElement( obj[JSON_IS_POTENTIALLY_HAZARDOUS]!! )


            // create map for last object elements
            val stringMap = mutableMapOf<String, String>()


            // extract JsonArray objects, decode, and assign
            val jArray2 = obj[JSON_APPROACH_DATA]!!.jsonArray
            jArray2.forEach { // for each element

                // extract DATE from object, decode, and add to stringMap
                stringMap[JSON_CLOSE_APPROACH_DATE] =
                    json.decodeFromJsonElement( it.jsonObject[JSON_CLOSE_APPROACH_DATE]!! )


                // extract VELOCITY from object, decode, and add to stringMap
                val jElm1 = it.jsonObject[JSON_RELATIVE_VELOCITY]!!.jsonObject[JSON_KILOMETERS_PER_SECOND]!!
                stringMap[JSON_KILOMETERS_PER_SECOND] =
                    json.decodeFromJsonElement(jElm1)


                // extract ASTRONOMICAL from object, decode, and add to stringMap
                val jElm2 = it.jsonObject[JSON_MISS_DISTANCE]!!.jsonObject[JSON_ASTRONOMICAL]!!
                stringMap[JSON_ASTRONOMICAL] =
                    json.decodeFromJsonElement(jElm2)
            }


            // assign DATE
            val closeApproachDate: String = stringMap[JSON_CLOSE_APPROACH_DATE]!!

            // assign VELOCITY
            val kilometersPerSecond: String = stringMap[JSON_KILOMETERS_PER_SECOND]!!

            // assign ASTRONOMICAL
            val astronomical: String = stringMap[JSON_ASTRONOMICAL]!!


            // add Asteroid to list
            asteroidList.add(
                Asteroid(
                    id,
                    name,
                    absoluteMagnitude,
                    estimatedDiameterMax,
                    isPotentiallyHazardousAsteroid,
                    closeApproachDate,
                    kilometersPerSecond,
                    astronomical
                )
            )
        }


        // map list of Asteroids to key Date String
        mapOfAsteroids[dateString] = asteroidList
    }

    // return map of asteroids Map<date: String, list: List<Asteroid>>
    return mapOfAsteroids
}


// returns list of Asteroids from Json String
fun getListOfAsteroids(query: String): List<Asteroid> {

    // tempList
    val tempList = mutableListOf<Asteroid>()

    // returns JsonObject of near_earth_objects
    val a = getNearEarthObjects(query)

    // returns Map<K, V> of String Dates & JsonArray of JsonObjects
    val b = getMapOfStringDatesAndJsonArrays(a)

    // returns Map<K, V> of String Dates & List of Asteroids
    val c = getAsteroidsFromJsonArrays(b)

    // add all lists to tempList
    c.values.map {
        tempList.addAll(it)
    }

    // return tempList
    return tempList
}
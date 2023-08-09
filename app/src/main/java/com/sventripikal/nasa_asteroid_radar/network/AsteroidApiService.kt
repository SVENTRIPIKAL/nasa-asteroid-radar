package com.sventripikal.nasa_asteroid_radar.network

import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


/**
 *  Retrofit string constants
 */
private const val API_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"  // personal api key required
private const val DEMO_KEY = "DEMO_KEY"                                 // practice demo api key
private const val DEMO_END_DATE = "2015-09-08"                          // practice end date
private const val DEMO_START_DATE = "2015-09-07"                        // practice start date
private const val BASE_URL = "https://api.nasa.gov/"                    // base request url
private const val DATE_PATTERN_STRING = "yyyy-MM-dd"                    // date format pattern


// builds network requests via Retrofit
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()


// defines get request functions for Api object
interface AsteroidApiService {

    // parameter query
    @GET("neo/rest/v1/feed")
    suspend fun getParameterQueryAsync(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): String
}


// object to build & expose retrofit service
object AsteroidApi {

    // returns object that implements network service requests
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}



// returns Triple of todays date, week from today date, and API_KEY
fun getTodayWeekQueryApiKey(): Triple<String, String, String> {

    // create calendar instance
    val calendar = Calendar.getInstance()

    // create formatter with date string pattern
    val formatter = SimpleDateFormat(DATE_PATTERN_STRING, Locale.getDefault())

    // get todays date via Calendar time
    val a = calendar.time

    // update calendar - add 7 days to current calendar time
    calendar.add(Calendar.DAY_OF_YEAR, 7)

    // get day of 1 week from today via Calendar time
    val b = calendar.time

    // format calendar times to pattern string
    val today = formatter.format(a).toString()
    val weekFromToday = formatter.format(b).toString()

    // return pair of formatted date strings
    return Triple(first = today, second = weekFromToday, third = API_KEY)
}


// returns Triple of demo start date, end date, and DEMO_KEY
fun getDemoQueryDemoKey(): Triple<String, String, String> {

    // return pair of formatted date strings
    return Triple(first = DEMO_START_DATE, second = DEMO_END_DATE, third = DEMO_KEY)
}



/**
 *  JSON PARSING
 */
// json query constants
private const val JSON_NEAR_EARTH_OBJECTS = "near_earth_objects"
private const val JSON_ID = "id"
private const val JSON_NAME = "name"
private const val JSON_ABSOLUTE_MAGNITUDE_H = "absolute_magnitude_h"
private const val JSON_ESTIMATED_DIAMETER = "estimated_diameter"
private const val JSON_KILOMETERS = "kilometers"
private const val JSON_ESTIMATED_DIAMETER_MAX = "estimated_diameter_max"
private const val JSON_IS_POTENTIALLY_HAZARDOUS = "is_potentially_hazardous_asteroid"
private const val JSON_APPROACH_DATA = "close_approach_data"
private const val JSON_RELATIVE_VELOCITY = "relative_velocity"
private const val JSON_KILOMETERS_PER_SECOND = "kilometers_per_second"
private const val JSON_CLOSE_APPROACH_DATE = "close_approach_date"
private const val JSON_MISS_DISTANCE = "miss_distance"
private const val JSON_ASTRONOMICAL = "astronomical"

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
             *      id: String
             *      name: String
             *      absoluteMagnitude: Double
             *      estimatedDiameterMax: Double
             *      isPotentiallyHazardousAsteroid: Boolean
             *      closeApproachDate: String
             *      kilometersPerSecond: String
             *      astronomical: String
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
fun convertToListOfAsteroids(query: String): List<Asteroid> {

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
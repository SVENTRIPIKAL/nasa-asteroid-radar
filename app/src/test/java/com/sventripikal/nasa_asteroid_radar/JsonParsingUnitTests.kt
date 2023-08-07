package com.sventripikal.nasa_asteroid_radar

import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import com.sventripikal.nasa_asteroid_radar.utils.JSON_ABSOLUTE_MAGNITUDE_H
import com.sventripikal.nasa_asteroid_radar.utils.JSON_APPROACH_DATA
import com.sventripikal.nasa_asteroid_radar.utils.JSON_ASTRONOMICAL
import com.sventripikal.nasa_asteroid_radar.utils.JSON_CLOSE_APPROACH_DATE
import com.sventripikal.nasa_asteroid_radar.utils.JSON_ESTIMATED_DIAMETER
import com.sventripikal.nasa_asteroid_radar.utils.JSON_ESTIMATED_DIAMETER_MAX
import com.sventripikal.nasa_asteroid_radar.utils.JSON_ID
import com.sventripikal.nasa_asteroid_radar.utils.JSON_IS_POTENTIALLY_HAZARDOUS
import com.sventripikal.nasa_asteroid_radar.utils.JSON_KILOMETERS
import com.sventripikal.nasa_asteroid_radar.utils.JSON_KILOMETERS_PER_SECOND
import com.sventripikal.nasa_asteroid_radar.utils.JSON_MISS_DISTANCE
import com.sventripikal.nasa_asteroid_radar.utils.JSON_NAME
import com.sventripikal.nasa_asteroid_radar.utils.JSON_NEAR_EARTH_OBJECTS
import com.sventripikal.nasa_asteroid_radar.utils.JSON_RELATIVE_VELOCITY
import com.sventripikal.nasa_asteroid_radar.utils.fakeDataRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.junit.Test


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



class JsonParsingUnitTests {

    /**
     *  converts Json request String to JsonObject  -  near_earth_objects: JsonObject
     */
    @Test
    fun when_requestReceived_should_containNearEarthObjectsOnly() {

        val obj = getNearEarthObjects(fakeDataRequest)

        println(obj)
    }

    /**
     *  converts JsonObject to Map<K, V>  -  Map<dates: String, array: JsonArray>
     */
    @Test
    fun when_getMapFromObject_should_containMapOfStringDatesJsonArrays() {

        val obj = getNearEarthObjects(fakeDataRequest)

        val mapOfJsonArrays = getMapOfStringDatesAndJsonArrays(obj)

        println(mapOfJsonArrays)
    }

    /**
     *  converts JsonArray elements to List<Asteroid>  -  Map<dates: String, List<Asteroid>>
     */
    @Test
    fun when_getAsteroidsFromArray_should_containMapOfDatesAndAsteroids() {

        val obj = getNearEarthObjects(fakeDataRequest)

        val mapOfJsonArrays = getMapOfStringDatesAndJsonArrays(obj)

        val mapOfAsteroids = getAsteroidsFromJsonArrays(mapOfJsonArrays)

        println(mapOfAsteroids)
    }


}
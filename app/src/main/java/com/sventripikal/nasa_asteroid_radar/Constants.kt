package com.sventripikal.nasa_asteroid_radar

import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import timber.log.Timber



const val TAG = "_SVENTRIPIKAL"


enum class Priority { ERROR, VERBOSE, DEBUG, INFO }


fun timber(tag: String, message: String, priority: Priority) {
    when (priority) {
        Priority.ERROR -> Timber.tag(tag).e(message)
        Priority.VERBOSE -> Timber.tag(tag).v(message)
        Priority.DEBUG -> Timber.tag(tag).d(message)
        Priority.INFO -> Timber.tag(tag).i(message)
    }
}


// sample/temp asteroid list
val asteroidList: List<Asteroid> = listOf(
    Asteroid(
        id = "0000001",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = false,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000002",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = false,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000003",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = true,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000004",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = false,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000005",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = false,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000006",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = true,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000007",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = false,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000008",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = false,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000009",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = true,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
    Asteroid(
        id = "0000010",
        absoluteMagnitude = 20.48,
        estimatedDiameterMax = 0.4764748465,
        isPotentiallyHazardousAsteroid = true,
        kilometersPerSecond = "18.127936605",
        astronomical = "0.3027469593"
    ),
)
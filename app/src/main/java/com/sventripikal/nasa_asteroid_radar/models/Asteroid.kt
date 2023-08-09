package com.sventripikal.nasa_asteroid_radar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable @Entity("asteroidTable")
data class Asteroid(
    @PrimaryKey var id: String,

    @ColumnInfo("name") var name: String,

    @ColumnInfo("absolute_magnitude_h")
            var absoluteMagnitude: Double,

    @ColumnInfo("estimated_diameter_max")
            var estimatedDiameterMax: Double,

    @ColumnInfo("is_potentially_hazardous_asteroid")
            var isPotentiallyHazardousAsteroid: Boolean,

    @ColumnInfo("close_approach_date")
            var closeApproachDate: String,

    @ColumnInfo("kilometers_per_second")
            var kilometersPerSecond: String,

    @ColumnInfo("astronomical") var astronomical: String
)
/**                 EXAMPLE_ASTEROID
 * id:                                  "2465633" {not_displayed}
 * name:                                "465633 (2009 JR5)"
 * absolute_magnitude_h:                20.48
 * estimated_diameter_max:              0.4764748465
 * is_potentially_hazardous_asteroid:   true
 * close_approach_date:                 "2015-09-08"
 * kilometers_per_second:               "18.127936605"
 * astronomical:                        "0.3027469593"
 *
 * { }
 * { near_earth_objects }
 *          [ 2015-09-08 ]
 *              { 0 }
 *                  +_id: <String>
 *                  +_name: <String>
 *                  +_absolute_magnitude_h: <Double>
 *                          |
 *                  { estimated_diameter }
 *                              { kilometers }
 *                                      +_estimated_diameter_max: <Double>
 *                          |
 *                  +_is_potentially_hazardous_asteroid: <Boolean>
 *                          |
 *                  [ close_approach_data ]
 *                          { 0 }
 *                              +_close_approach_date: <String>
 *                                  |
 *                              { relative_velocity }
 *                                      +_kilometers_per_second: <String>
 *                                  |
 *                              { miss_distance }
 *                                      +_astronomical: <String>
 *
 */
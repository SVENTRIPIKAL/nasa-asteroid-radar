package com.sventripikal.nasa_asteroid_radar.models

data class Asteroid(
    var id: String,
    var absoluteMagnitude: Double,
    var estimatedDiameterMax: Double,
    var isPotentiallyHazardousAsteroid: Boolean,
    var kilometersPerSecond: String,
    var astronomical: String
)
/**                 EXAMPLE_ASTEROID
 * id:                                  "2465633" {not_displayed}
 * absolute_magnitude_h:                20.48
 * estimated_diameter_max:              0.4764748465
 * is_potentially_hazardous_asteroid:   true
 * kilometers_per_second:               "18.127936605"
 * astronomical:                        "0.3027469593"
 */

// [ close_approach_data ] -> { relative_velocity } -> < kilometers_per_second >
// [ close_approach_data ] -> {   miss_distance   } -> <     astronomical      >
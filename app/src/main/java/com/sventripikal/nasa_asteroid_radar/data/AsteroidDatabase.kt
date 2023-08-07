package com.sventripikal.nasa_asteroid_radar.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// asteroid database for caching queried data
@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {

    // returns Dao for accessing database
    abstract fun AsteroidDao(): AsteroidDao
}
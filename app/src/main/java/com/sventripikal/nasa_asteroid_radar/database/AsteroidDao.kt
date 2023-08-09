package com.sventripikal.nasa_asteroid_radar.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// data access object functions
// for accessing database
@Dao
interface AsteroidDao {

    // retrieves all from database
    @Query("""
        select * from asteroidTable
        order by close_approach_date asc
    """)
    fun getAll(): LiveData<List<Asteroid>>


    // retrieves asteroids for week ahead from database
    @Query("""
        select * from asteroidTable
        where close_approach_date
        between :startDate and :endDate
        order by close_approach_date asc
    """)
    fun getAsteroidsOfTheWeek(startDate: String, endDate: String): LiveData<List<Asteroid>>


    // inserts all into database
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertAll(vararg asteroid: Asteroid)
}


// database access to dao instance
@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {

    abstract val asteroidDao: AsteroidDao
}

// database instance
private lateinit var INSTANCE: AsteroidDatabase

// returns singleton database
fun getDatabase(application: Application): AsteroidDatabase {

    synchronized(AsteroidDatabase::class.java) {

        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                application.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids"
            ).build()
        }
    }

    return INSTANCE
}

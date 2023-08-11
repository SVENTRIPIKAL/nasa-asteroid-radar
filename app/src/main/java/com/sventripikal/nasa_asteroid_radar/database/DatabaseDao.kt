package com.sventripikal.nasa_asteroid_radar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import com.sventripikal.nasa_asteroid_radar.models.ImageOfTheDay


// data access object functions
// for accessing database
@Dao
interface DatabaseDao {

    /**
     * ASTEROID TABLE
     */
    // retrieves all from database sorted decending
    @Query("""
        select * from asteroidTable
        order by close_approach_date desc
    """)
    fun getAllSavedAsteroids(): LiveData<List<Asteroid>>


    // get todays asteroids from database sorted ascending
    @Query("""
        select * from asteroidTable
        where close_approach_date = :todaysDate
        order by close_approach_date asc
    """)
    fun getTodaysAsteroids(todaysDate: String): LiveData<List<Asteroid>>


    // retrieves asteroids for the week from database sorted descending
    @Query("""
        select * from asteroidTable
        where close_approach_date
        between :startDate and :endDate
        order by close_approach_date desc
    """)
    fun getThisWeeksAsteroids(startDate: String, endDate: String): LiveData<List<Asteroid>>


    // deletes all asteroids from database more than a week old
    @Query(
        """
        delete from asteroidTable
        where close_approach_date < :weekPriorDate
    """
    )
    fun deleteWeekOldAsteroids(weekPriorDate: String)


    // inserts asteroid into database
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertAsteroid(vararg asteroid: Asteroid)


    /**
     * IMAGE TABLE
     */
    // retrieves image of the day
    @Query("""
        select * from imageTable
        where date = :date
    """)
    fun getImageOfTheDay(date: String): LiveData<ImageOfTheDay>


    // deletes all images from database before today
    @Query(
        """
        delete from imageTable
        where date < :todaysDate
    """
    )
    fun deleteImagesBeforeToday(todaysDate: String)


    // inserts image into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(vararg image: ImageOfTheDay)
}


// database access to dao instance
@Database(entities = [Asteroid::class, ImageOfTheDay::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {

    abstract val databaseDao: DatabaseDao
}

// database instance
private lateinit var INSTANCE: AsteroidDatabase

// returns singleton database instance
fun getDatabase(context: Context): AsteroidDatabase {

    synchronized(AsteroidDatabase::class.java) {

        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids"
            ).build()
        }
    }

    return INSTANCE
}

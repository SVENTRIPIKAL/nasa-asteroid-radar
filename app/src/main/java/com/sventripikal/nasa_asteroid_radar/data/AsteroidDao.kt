package com.sventripikal.nasa_asteroid_radar.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import java.sql.Date


@Dao
interface AsteroidDao {

//    // queries asteroids for the week ordered by ascending date
//    @Query("""
//        select * from asteroidTable
//        where close_approach_date
//        between :startDate and :endDate
//        order by close_approach_date
//    """) fun fetchUpcomingWeek(startDate: Date, endDate: Date): LiveData<String>
}
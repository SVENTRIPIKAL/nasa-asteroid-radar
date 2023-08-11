package com.sventripikal.nasa_asteroid_radar

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


// current date format pattern
private const val DATE_PATTERN_STRING = "yyyy-MM-dd"


// returns current date as formatted string
fun getCurrentDateString(): String {

    // create calendar instance
    val calendar = Calendar.getInstance()

    // create formatter with date string pattern
    val formatter = SimpleDateFormat(DATE_PATTERN_STRING, Locale.getDefault())

    // return todays calendar time as string
    return formatter.format(calendar.time).toString()
}


// returns pair of todays date and week from today
fun getTodayPlusOneWeek(): Pair<String, String> {

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
    return Pair(first = today, second = weekFromToday)
}

// returns pair of todays date and week from today
fun getTodayMinusOneWeek(): String {

    // create calendar instance
    val calendar = Calendar.getInstance()

    // create formatter with date string pattern
    val formatter = SimpleDateFormat(DATE_PATTERN_STRING, Locale.getDefault())

    // update calendar - add 7 days to current calendar time
    calendar.add(Calendar.DAY_OF_YEAR, -7)

    // return pair of formatted date strings
    return formatter.format( calendar.time ).toString()
}


// unit tests for date functions
class DateRetrievalUnitTests {

    /**
     *  get pair of todays date and week from today as formatted Strings
     */
    @Test
    fun when_gettingTodaysDate_should_returnPairTodayPlusOneWeek() {

        val expected = Pair("2023-08-07","2023-08-14")
        val actual = getTodayPlusOneWeek()

        println("EXPECTED: $expected      ACTUAL: $actual")

        assertEquals(expected, actual)
    }

    @Test
    fun when_gettingPastWeek_should_returnWeekBefore() {

        val expected = "2023-08-03"
        val actual = getTodayMinusOneWeek()

        println("EXPECTED: $expected      ACTUAL: $actual")

        assertEquals(expected, actual)
    }
}
package com.clovermusic.clover.util

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun parseReleaseDate(date: String): LocalDate {
    val formats = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    )
    for (format in formats) {
        try {
            return LocalDate.parse(date, format)
        } catch (e: DateTimeParseException) {
            Log.e("parseReleaseDate", "Failed to parse date: $date")
        }
    }

    // Handle the case where the date is just a year
    return try {
        val year = date.toInt()
        LocalDate.of(year, 1, 1)
    } catch (e: NumberFormatException) {
        // Default to a very old date if parsing fails
        LocalDate.MIN
    }
}
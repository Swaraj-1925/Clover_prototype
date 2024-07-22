package com.clovermusic.clover.util

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Parsers {
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

    fun convertMillisToHoursMinutes(millis: Int): String {
        val hours = millis / (1000 * 60 * 60)
        val minutes = (millis % (1000 * 60 * 60)) / (1000 * 60)
        return if (hours < 1) {
            String.format("%02d min", minutes)
        } else {
            "${hours}hr ${minutes}min"
        }
    }
}
package com.clovermusic.clover.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Parsers {
    fun parseReleaseDate(date: String): LocalDate {
//        Log.d("Parsers", "parseReleaseDate: $date")
        if (date.equals("day", ignoreCase = true)) {
            return LocalDate.now()
        }

        val formats = listOf(
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
        for (format in formats) {
            try {
                return LocalDate.parse(date, format)
            } catch (e: DateTimeParseException) {
            }
        }

        return try {
            val year = date.toInt()
            LocalDate.of(year, 1, 1)
        } catch (e: NumberFormatException) {
            // Default to a very old date if parsing fails
            LocalDate.MIN
        }
    }

    fun parseDurationMinutesSeconds(millis: Int): String {
        val minutes = millis / (1000 * 60)
        val seconds = (millis % (1000 * 60)) / 1000
        return if (seconds < 1) {
            "${minutes}min"
        } else {
            "${minutes}min ${seconds}sec"
        }
    }

    fun parseDurationMinutesSeconds(millis: Long): String {
        val minutes = millis / (1000 * 60)
        val seconds = (millis % (1000 * 60)) / 1000
        return if (seconds < 1) {
            "${minutes}min"
        } else {
            "${minutes}min ${seconds}sec"
        }
    }

    fun formatDuration(durationMs: Long): String {
        val totalSeconds = durationMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    fun parseDurationHoursMinutes(millis: Int): String {
        val hours = millis / (1000 * 60 * 60)
        val minutes = (millis % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (millis % (1000 * 60)) / 1000
        return if (hours < 1) {
            "${minutes}min ${seconds}sec"
        } else {
            "${hours}hr ${minutes}min"
        }
    }

    fun String.convertSpotifyImageUriToUrl(): String {
        val imageId = this.split(":").last()
        return "https://i.scdn.co/image/$imageId"
    }

}
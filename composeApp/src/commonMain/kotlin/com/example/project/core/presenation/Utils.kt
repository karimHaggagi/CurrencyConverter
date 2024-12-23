package com.example.project.core.presenation

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

/**
 * created by Karim Haggagi on 12/18/24
 **/

fun String.formatDateTime(): String {
    if (this.isNullOrBlank()) return ""
    // Parse the ISO date-time string to an Instant
    val instant = Instant.parse(this)


    // Convert to LocalDateTime in UTC
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

    // Extract components
    val day = localDateTime.date.dayOfMonth
    val month =
        localDateTime.date.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val year = localDateTime.date.year

    val hour = if (localDateTime.hour % 12 == 0) 12 else localDateTime.hour % 12
    val minute = localDateTime.minute
    val amPm = if (localDateTime.hour < 12) "AM" else "PM"

    // Manually construct the formatted string
    val dayString = if (day < 10) "0$day" else "$day"
    val hourString = if (hour < 10) "0$hour" else "$hour"
    val minuteString = if (minute < 10) "0$minute" else "$minute"

    return "$dayString $month $year $hourString:$minuteString$amPm"
}

fun Instant.differentDays(lastUpdate: String): Boolean {

    val date1 = this.toLocalDateTime(TimeZone.UTC).date
    val date2 = Instant.parse(lastUpdate).toLocalDateTime(TimeZone.UTC).date

    return date1.dayOfYear > date2.dayOfYear
}


fun Double.formatToTwoDecimals(): String {
    return ((this * 100).roundToInt() / 100.0).toString()
}

fun String.removeComma(): String {
    return this.replace(',', ' ').trim()
}

fun getDateBeforeDays(daysToSubtract: Int): String {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    val previousDate = today.minus(kotlinx.datetime.DatePeriod(days = daysToSubtract))
    return previousDate.toString() // Returns in format "yyyy-MM-dd"
}

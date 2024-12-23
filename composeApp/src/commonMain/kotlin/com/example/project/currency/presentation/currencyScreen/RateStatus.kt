package com.example.project.currency.presentation.currencyScreen

import androidx.compose.ui.graphics.Color
import com.example.project.core.presenation.differentDays
import kotlinx.datetime.Clock

/**
 * created by Karim Haggagi on 12/19/24
 **/
enum class RateStatus(val title: String, val color: Color) {
    IDLE(
        "Rates",
        Color.White
    ),
    FRESH(
        "Fresh Rates",
        Color.Green
    ),
    STALE(
        "Rates are not fresh",
        Color.Red
    );

    companion object {
        fun getStatusFromDate(date: String): RateStatus {
            val currentInstant = Clock.System.now()

            return when (currentInstant.differentDays(date)) {
                true -> RateStatus.STALE
                false -> RateStatus.FRESH
            }
        }
    }
}
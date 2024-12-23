package com.example.project.currency.presentation.currencyScreen

import com.example.project.core.domain.UiText
import com.example.project.core.presenation.*
import com.example.project.currency.domain.model.Country
import com.example.project.currency.domain.model.CurrencyDetails

class CurrencyContract {

    sealed class CurrencyEvent : UiEvent {
        data object HideErrorDialog : CurrencyEvent()
        data class OnFromCurrencyChanged(val option: CurrencyDetails) : CurrencyEvent()
        data class OnToCurrencyChanged(val option: CurrencyDetails) : CurrencyEvent()
        data object Refresh : CurrencyEvent()
        data object OnSwapIconClick : CurrencyEvent()
        data class OnFromCurrencyValueChanged(val value: String) : CurrencyEvent()
        data class OnToCurrencyValueChanged(val value: String) : CurrencyEvent()
    }

    data class CurrencyState(
        val loading: Boolean = true,
        val error: UiText? = null,
        val date: String = "",
        val fromCurrencyValue: String = "0.00",
        val toCurrencyValue: String = "0.00",
        val rateStatus: RateStatus = RateStatus.IDLE,
        val allCurrencies: List<CurrencyDetails> = emptyList(),
        val fromCurrencies: CurrencyDetails = CurrencyDetails(
            country = Country.DEFAULT,
            code = "",
            rate = 0.0
        ),
        val toCurrencies: CurrencyDetails = CurrencyDetails(
            country = Country.DEFAULT,
            code = "",
            rate = 0.0
        ),
        val latestValues: List<UiStatus<LatestCurrencyDetails>> = listOf(UiStatus.Idle)
    ) : UiState

    data class LatestCurrencyDetails(
        val lastUpdate: String = "",
        val fromCurrencyDetails: CurrencyDetails = CurrencyDetails(
            country = Country.DEFAULT,
            code = "",
            rate = 1.0
        ),
        val toCurrencyDetails: CurrencyDetails = CurrencyDetails(
            country = Country.DEFAULT,
            code = "",
            rate = 0.0
        ),
    )

    sealed class CurrencyEffect : UiEffect {
    }
}
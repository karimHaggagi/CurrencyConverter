package com.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.project.currency.domain.model.Country
import com.example.project.currency.domain.model.CurrencyDetails
import com.example.project.currency.presentation.currencyScreen.CurrencyContract
import com.example.project.currency.presentation.currencyScreen.CurrencyScreen
import com.example.project.currency.presentation.currencyScreen.RateStatus
import com.example.project.currency.presentation.currencyScreen.component.ExchangeCurrency
import com.example.project.currency.presentation.currencyScreen.component.HomeHeader
import com.example.project.currency.presentation.currencyScreen.component.LatestCurrencySuccess

/**
 * created by Karim Haggagi on 12/18/24
 **/

@Preview
@Composable
private fun HomScreenPreview() {
    CurrencyScreen(state = CurrencyContract.CurrencyState())
}

@Preview
@Composable
fun HomeHeaderPreview(modifier: Modifier = Modifier) {
    HomeHeader(date = "18 Dec 2024 11:59PM", rateStatus = RateStatus.STALE)
}

@Preview
@Composable
private fun ExchangeCurrencyPreview() {
    ExchangeCurrency(currencyState = CurrencyContract.CurrencyState())
}

@Preview
@Composable
private fun LatestCurrencySuccessPreview() {
    LatestCurrencySuccess(
        lastUpdate = CurrencyContract.LatestCurrencyDetails(
            lastUpdate = "22/12/2024",
            fromCurrencyDetails = CurrencyDetails(Country.EGP, code = "EGP", rate = 19.0)
        )
    )
}
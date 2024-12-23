package com.example.project.currency.presentation.currencyScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.project.core.domain.UiText
import com.example.project.core.presenation.ScreenContainer
import com.example.project.core.presenation.UiStatus
import com.example.project.core.presenation.formatDateTime
import com.example.project.currency.presentation.currencyScreen.component.ExchangeCurrency
import com.example.project.currency.presentation.currencyScreen.component.HomeHeader
import com.example.project.currency.presentation.currencyScreen.component.LatestCurrencyError
import com.example.project.currency.presentation.currencyScreen.component.LatestCurrencySuccess
import com.example.project.currency.ui.theme.DarkBlue
import org.koin.compose.viewmodel.koinViewModel

/**
 * created by Karim Haggagi on 12/15/24
 **/
@Composable
fun CurrencyRoute(modifier: Modifier = Modifier, viewModel: CurrencyViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    ScreenContainer(
        isLoading = state.value.loading,
        errorDialogText = state.value.error,
        screen = {
            CurrencyScreen(modifier, state.value, onEvent = viewModel::setEvent)
        },
        onDialogButtonClick = { viewModel.setEvent(CurrencyContract.CurrencyEvent.HideErrorDialog) })
}

@Composable
fun CurrencyScreen(
    modifier: Modifier = Modifier,
    state: CurrencyContract.CurrencyState = CurrencyContract.CurrencyState(),
    onEvent: (CurrencyContract.CurrencyEvent) -> Unit = {}
) {
    Column(
        modifier = modifier.background(DarkBlue).statusBarsPadding().fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp),
            colors = CardDefaults.cardColors(containerColor = DarkBlue)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeHeader(
                    date = state.date,
                    rateStatus = state.rateStatus,
                    onRefresh = { onEvent(CurrencyContract.CurrencyEvent.Refresh) })
                ExchangeCurrency(
                    currencyState = state,
                    onFromCurrencyChange = {
                        onEvent(
                            CurrencyContract.CurrencyEvent.OnFromCurrencyChanged(
                                it
                            )
                        )
                    },
                    toCurrencyChange = {
                        onEvent(
                            CurrencyContract.CurrencyEvent.OnToCurrencyChanged(
                                it
                            )
                        )
                    }, onSwapIconClick = {
                        onEvent(CurrencyContract.CurrencyEvent.OnSwapIconClick)
                    },
                    onFromCurrencyValueChanged = {
                        onEvent(
                            CurrencyContract.CurrencyEvent.OnFromCurrencyValueChanged(
                                it
                            )
                        )
                    },
                    onToCurrencyValueChanged = {
                        onEvent(
                            CurrencyContract.CurrencyEvent.OnToCurrencyValueChanged(
                                it
                            )
                        )
                    })
            }

        }
        if (state.latestValues.any { it is UiStatus.Loading }){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }else {
            state.latestValues.forEach { item ->
                when (item) {
                    is UiStatus.Error -> {
                        LatestCurrencyError(error = item.error)
                    }

                    is UiStatus.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    is UiStatus.Success -> {
                        LatestCurrencySuccess(lastUpdate = item.data)
                    }

                    UiStatus.Idle -> {
                        LatestCurrencyError(modifier = Modifier.fillMaxSize(),error = UiText.DynamicString("No Data to display"))
                    }
                }
            }
        }
    }

}

@Composable
fun CurrencyItem(currency: String, rate: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = currency, style = MaterialTheme.typography.bodyMedium)
        Text(text = rate.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}
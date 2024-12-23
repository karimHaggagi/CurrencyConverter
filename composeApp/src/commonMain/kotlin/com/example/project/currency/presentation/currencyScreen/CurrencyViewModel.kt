package com.example.project.currency.presentation.currencyScreen

import androidx.lifecycle.viewModelScope
import com.example.project.core.domain.DataError
import com.example.project.core.domain.UiText
import com.example.project.core.domain.fold
import com.example.project.core.domain.map
import com.example.project.core.domain.onError
import com.example.project.core.domain.onSuccess
import com.example.project.core.domain.toUiText
import com.example.project.core.presenation.BaseViewModel
import com.example.project.core.presenation.UiStatus
import com.example.project.core.presenation.formatDateTime
import com.example.project.core.presenation.getDateBeforeDays
import com.example.project.core.presenation.removeComma
import com.example.project.currency.domain.model.CurrencyDetails
import com.example.project.currency.domain.model.CurrencyModel
import com.example.project.currency.domain.model.asLatestCurrencyDetails
import com.example.project.currency.domain.repository.CurrencyRepository
import com.example.project.currency.domain.usecase.CalculateExchangeRateUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.supervisorScope

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository,
    private val calculateExchangeRateUseCase: CalculateExchangeRateUseCase
) :
    BaseViewModel<CurrencyContract.CurrencyState, CurrencyContract.CurrencyEvent, CurrencyContract.CurrencyEffect>() {

    override val state: StateFlow<CurrencyContract.CurrencyState> =
        _state.onStart {
            observedCachedList()
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(STATE_SUBSCRIPTION_TIMEOUT_MS),
                initialState
            )


    init {
        observeOnFromCurrencyValueChanged()
    }

    private var currencyJob: Job? = null

    private fun getAllCurrencies() {
        currencyJob?.cancel()
        currencyJob = viewModelScope.launch {
            currencyRepository.getAllCurrencies()
                .onError { error: DataError.Remote ->
                    setState {
                        copy(
                            loading = false,
                            error = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun setData(currencyModel: CurrencyModel) {
        setState {
            copy(
                loading = false,
                date = currencyModel.lastUpdated.formatDateTime(),
                allCurrencies = currencyModel.currencies,
                fromCurrencies = currencyModel.currencies[0],
                toCurrencies = currencyModel.currencies[0],
                rateStatus = RateStatus.getStatusFromDate(currencyModel.lastUpdated),
                latestValues = listOf(UiStatus.Idle)
            )
        }
    }

    override fun createInitialState(): CurrencyContract.CurrencyState {
        return CurrencyContract.CurrencyState()
    }

    override fun handleEvent(event: CurrencyContract.CurrencyEvent) {
        when (event) {
            CurrencyContract.CurrencyEvent.HideErrorDialog -> setState { copy(error = null) }
            is CurrencyContract.CurrencyEvent.OnFromCurrencyChanged -> {
                setState { copy(fromCurrencies = event.option) }
            }

            is CurrencyContract.CurrencyEvent.OnToCurrencyChanged -> {
                setState { copy(toCurrencies = event.option) }
                getLatestThreeDays()
            }

            CurrencyContract.CurrencyEvent.Refresh -> {
                setState { CurrencyContract.CurrencyState() }
                getAllCurrencies()
            }

            CurrencyContract.CurrencyEvent.OnSwapIconClick -> {
                val fromCurrency = currentState.fromCurrencies
                setState {
                    copy(
                        fromCurrencies = currentState.toCurrencies,
                        toCurrencies = fromCurrency
                    )
                }
                getLatestThreeDays()
            }

            is CurrencyContract.CurrencyEvent.OnFromCurrencyValueChanged -> {
                setState { copy(fromCurrencyValue = event.value) }
            }

            is CurrencyContract.CurrencyEvent.OnToCurrencyValueChanged -> {
                setState { copy(toCurrencyValue = event.value) }
            }
        }
    }

    private fun getLatestThreeDays() {
        viewModelScope.launch {
            supervisorScope {
                setState { copy(latestValues = listOf(UiStatus.Loading)) }

                val days = (1..3).map { day ->
                    async { processDay(day) }
                }

                val results = days.awaitAll()
                setState { copy(latestValues = results) }
            }
        }
    }

    private suspend fun processDay(day: Int): UiStatus<CurrencyContract.LatestCurrencyDetails> {
        return getDayDetails(day)
            .map { currencyData ->
                currencyData.asLatestCurrencyDetails(
                    fromCurrency = currentState.fromCurrencies,
                    toCurrency = currentState.toCurrencies
                ).let { details ->
                    val result = getCalculatedDay(
                        details.fromCurrencyDetails.rate,
                        details.toCurrencyDetails.rate
                    )
                    details.copy(
                        toCurrencyDetails = details.toCurrencyDetails.copy(rate = result.toDouble()),
                        fromCurrencyDetails = details.fromCurrencyDetails.copy(rate = 1.0)
                    )
                }
            }
            .fold(
                onSuccess = { UiStatus.Success(it) },
                onError = { UiStatus.Error(it.toUiText()) }
            )
    }

    private suspend fun getCalculatedDay(fromRate: Double, toRate: Double): String {
        return calculateExchangeRateUseCase(
            amount = 1.0,
            fromCurrency = fromRate,
            toCurrency = toRate
        )
    }

    private suspend fun getDayDetails(day: Int) = coroutineScope {
        async {
            currencyRepository.getCurrencyByDate(
                currency = "${currentState.fromCurrencies.code},${currentState.toCurrencies.code}",
                date = getDateBeforeDays(day)
            )
        }.await()
    }

    private fun observedCachedList() {
        currencyRepository.getCashedCurrencies()
            .onEach { cashedList ->
                if (cashedList.currencies.isEmpty()) {
                    getAllCurrencies()
                } else {
                    setData(cashedList)
                }
            }
            .debounce(DEBOUNCE_TIME_MS)
            .launchIn(viewModelScope)


    }

    private fun isValidInput(value: String): Boolean {
        return value.isNotBlank() && value.removeComma().toDoubleOrNull()?.let {
            it >= 0 && it.toString().length <= MAX_INPUT_LENGTH
        } ?: false
    }

    private fun observeOnFromCurrencyValueChanged() {
        state
            .map {
                CurrencyInputData(
                    fromCurrencyValue = it.fromCurrencyValue,
                    toCurrencyValue = it.toCurrencyValue,
                    fromCurrency = it.fromCurrencies,
                    toCurrency = it.toCurrencies
                )
            }
            .debounce(DEBOUNCE_TIME_MS)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach { currencyInputData ->
                if (!isValidInput(currencyInputData.fromCurrencyValue)) {
                    setState { copy(toCurrencyValue = "0.00") }
                    return@onEach
                }

                val amount = currencyInputData.fromCurrencyValue.removeComma().toDouble()
                val result = calculateExchangeRateUseCase(
                    amount = amount,
                    fromCurrency = currentState.fromCurrencies.rate,
                    toCurrency = currentState.toCurrencies.rate
                )

                setState {
                    copy(toCurrencyValue = result)
                }

            }
            .catch { e ->
                setState {
                    copy(
                        error = UiText.DynamicString(e.message ?: "Unknown error"),
                        toCurrencyValue = "0.00"
                    )
                }
            }
            .launchIn(viewModelScope )
    }

    companion object {
        private const val MAX_INPUT_LENGTH = 10
        private const val DEBOUNCE_TIME_MS = 1000L
        private const val STATE_SUBSCRIPTION_TIMEOUT_MS = 5000L
    }

}


data class CurrencyInputData(
    val fromCurrencyValue: String,
    val toCurrencyValue: String,
    val fromCurrency: CurrencyDetails,
    val toCurrency: CurrencyDetails
)
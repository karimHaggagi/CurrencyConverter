package com.example.project.core.presenation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * created by Karim Haggagi on 12/15/24
 **/
abstract class BaseViewModel<UiState, UiEvent, UiEffect> : ViewModel() {

    protected val initialState by lazy { createInitialState() }

    protected val _state = MutableStateFlow(initialState)
    abstract val state: StateFlow<UiState>

    abstract fun createInitialState(): UiState


    val currentState get() = state.value

    private val _event: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collectLatest {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: UiEvent)

    fun setEvent(newEvent: UiEvent) {
        viewModelScope.launch { _event.emit(newEvent) }
    }

    fun setState(reduce: UiState.() -> UiState) {
        _state.update { currentState.reduce() }
    }

    fun setEffect(newEffect: UiEffect) {
        viewModelScope.launch { _effect.send(newEffect) }
    }

}
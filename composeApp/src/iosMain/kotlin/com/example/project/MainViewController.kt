package com.example.project

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }
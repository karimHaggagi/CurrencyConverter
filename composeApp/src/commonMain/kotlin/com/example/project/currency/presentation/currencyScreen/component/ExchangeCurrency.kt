package com.example.project.currency.presentation.currencyScreen.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.project.currency.domain.model.CurrencyDetails
import com.example.project.currency.presentation.currencyScreen.CurrencyContract
import currencyconverter.composeapp.generated.resources.Res
import currencyconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

/**
 * created by Karim Haggagi on 12/18/24
 **/
@Composable
fun ExchangeCurrency(
    modifier: Modifier = Modifier,
    currencyState: CurrencyContract.CurrencyState,
    onFromCurrencyChange: (CurrencyDetails) -> Unit = {},
    toCurrencyChange: (CurrencyDetails) -> Unit = {},
    onFromCurrencyValueChanged: (String) -> Unit = {},
    onToCurrencyValueChanged: (String) -> Unit = {},
    onSwapIconClick: () -> Unit = {}
) {
    var animationStarted by remember { mutableStateOf(false) }
    val rotate by animateFloatAsState(
        targetValue = if (animationStarted) 180f else 0f,
        animationSpec = tween(1_000)
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CurrencyDropDown(
            modifier = Modifier.weight(0.4f),
            currencies = currencyState.allCurrencies,
            onClick = onFromCurrencyChange
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FlagCard(country = currencyState.fromCurrencies.country)
                TextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(color = Color.Black),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        color = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black
                    ),
                    value = currencyState.fromCurrencyValue,
                    onValueChange = onFromCurrencyValueChanged
                )
            }
        }

        IconButton(modifier = Modifier.weight(0.2f),
            onClick = {
                onSwapIconClick()
                animationStarted = !animationStarted
            }) {
            Icon(
                modifier = Modifier.graphicsLayer {
                    rotationY = rotate
                },
                painter = painterResource(Res.drawable.swap),
                contentDescription = null,
                tint = Color.White
            )
        }
        CurrencyDropDown(
            modifier = Modifier.weight(0.4f),
            currencies = currencyState.allCurrencies,
            onClick = toCurrencyChange
        ) {
            Column {
                FlagCard(country = currencyState.toCurrencies.country!!)
                TextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(color = Color.Black),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        color = Color.White
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = currencyState.toCurrencyValue,
                    onValueChange = onToCurrencyValueChanged
                )
            }
        }
    }
}

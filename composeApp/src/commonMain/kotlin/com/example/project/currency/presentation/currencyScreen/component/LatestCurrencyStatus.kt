package com.example.project.currency.presentation.currencyScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.project.core.domain.UiText
import com.example.project.currency.presentation.currencyScreen.CurrencyContract
import com.example.project.currency.ui.theme.DarkBlue
import org.jetbrains.compose.resources.painterResource

/**
 * created by Karim Haggagi on 12/22/24
 **/

@Composable
fun LatestCurrencySuccess(
    modifier: Modifier = Modifier,
    lastUpdate: CurrencyContract.LatestCurrencyDetails
) {
    Column(modifier = modifier.fillMaxWidth().padding(all = 16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth().background(color = DarkBlue).padding(8.dp),
            text = lastUpdate.lastUpdate,
            color = Color.White,
            style = TextStyle.Default.copy(textAlign = TextAlign.Center)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier.size(40.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape),
                painter = painterResource(lastUpdate.toCurrencyDetails.country.flag),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = lastUpdate.toCurrencyDetails.code,
                fontWeight = FontWeight.Bold,
                style = TextStyle.Default.copy(textAlign = TextAlign.Center)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "${lastUpdate.fromCurrencyDetails.rate} ${lastUpdate.fromCurrencyDetails.code} = ${lastUpdate.toCurrencyDetails.rate} ${lastUpdate.toCurrencyDetails.code}",
                fontWeight = FontWeight.Bold,
                style = TextStyle.Default.copy(textAlign = TextAlign.End)
            )
        }
    }
}

@Composable
fun LatestCurrencyError(modifier: Modifier = Modifier, error: UiText) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(DarkBlue)
                .padding(vertical = 20.dp),
            text = error.asString(),
            color = Color.White,
            style = TextStyle.Default.copy(textAlign = TextAlign.Center)
        )
    }
}
package com.example.project.currency.presentation.currencyScreen.component

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.project.currency.presentation.currencyScreen.RateStatus
import currencyconverter.composeapp.generated.resources.Res
import currencyconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

/**
 * created by Karim Haggagi on 12/18/24
 **/

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    date: String,
    rateStatus: RateStatus,
    onRefresh: () -> Unit ={}
) {
    val transition = rememberInfiniteTransition()

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(5000),
            repeatMode = RepeatMode.Restart
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(80.dp).rotate(rotation),
            painter = painterResource(Res.drawable.exchange_rate),
            contentDescription = null,
            tint = Color.White
        )

        Column {
            Text(date, color = Color.White)
            Text(rateStatus.title, color = rateStatus.color)

        }
        Spacer(modifier = Modifier.weight(1f))
        if (rateStatus == RateStatus.STALE) {
            IconButton(modifier = Modifier, onClick = onRefresh) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
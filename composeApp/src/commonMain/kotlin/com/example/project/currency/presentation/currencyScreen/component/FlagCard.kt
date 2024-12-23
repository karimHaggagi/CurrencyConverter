package com.example.project.currency.presentation.currencyScreen.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.project.core.presenation.ZoomableAnimation
import com.example.project.currency.domain.model.Country
import org.jetbrains.compose.resources.painterResource

/**
 * created by Karim Haggagi on 12/18/24
 **/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FlagCard(modifier: Modifier = Modifier, country: Country) {


    Box(modifier = Modifier) {
        Card(
            modifier = modifier.padding(top = 28.dp).fillMaxWidth().height(80.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxHeight(),

                ) {
                ZoomableAnimation(
                    modifier = modifier
                        .align(Alignment.CenterVertically), state = country.fullName
                ) { targetState ->
                    Text(
                        text = targetState,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                    )
                }
            }
        }
        ZoomableAnimation(
            Modifier.align(Alignment.TopCenter),
            state = country.flag
        ) { targetState ->
            Image(
                modifier = Modifier.size(40.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape),
                painter = painterResource(targetState),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

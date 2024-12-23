package com.example.project.core.presenation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * created by Karim Haggagi on 12/19/24
 **/
@Composable
fun <T> ZoomableAnimation(
    modifier: Modifier = Modifier,
    state: T,
    content: @Composable (T) -> Unit
) {

    // Box to hold the image
    AnimatedContent(
        modifier = modifier,
        targetState = state,
        transitionSpec = {
            (fadeIn(animationSpec = tween(5_00, delayMillis = 2_00)) +
                    scaleIn(initialScale = 0.2f, animationSpec = tween(5_00, delayMillis = 2_00)))
                .togetherWith(
                    fadeOut(animationSpec = tween(4_00)) +
                            scaleOut(
                                animationSpec = tween(4_00)
                            )
                )
        }
    ) { targetState ->
        content(targetState)
    }
}
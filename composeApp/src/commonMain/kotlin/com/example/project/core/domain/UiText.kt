package com.example.project.core.domain

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * created by Karim Haggagi on 12/15/24
 **/
sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class ResourceString(
        val resId: StringResource,
        vararg val args: Any
    ) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is ResourceString -> stringResource(resId, args)
        }

    }

}
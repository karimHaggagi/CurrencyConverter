package com.example.project.core.presenation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.project.core.domain.UiText

/**
 * created by Karim Haggagi on 12/15/24
 **/

@Composable
fun ErrorDialog(
    errorMessage: UiText,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .heightIn(min = 150.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when (
                    errorMessage
                ) {
                    is UiText.DynamicString -> errorMessage.value
                    is UiText.ResourceString -> errorMessage.asString()
                }, style = MaterialTheme.typography.bodyMedium
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = onDismiss
            ) {
                Text(text = "Ok", fontSize = 20.sp)
            }
        }
    }
}

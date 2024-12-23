package com.example.project.currency.presentation.currencyScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.project.currency.domain.model.CurrencyDetails
import org.jetbrains.compose.resources.painterResource

/**
 * created by Karim Haggagi on 12/18/24
 **/
@Composable
fun CurrencyDropDown(
    modifier: Modifier = Modifier,
    currencies: List<CurrencyDetails>,
    onClick: (CurrencyDetails) -> Unit,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clickable { expanded = true }) {
        content()
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { option ->
                DropdownMenuItem(modifier = Modifier.background(Color.Black),
                    text = {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                                .align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(40.dp)
                                    .clip(CircleShape)
                                    .border(width = 1.dp, color = Color.White, shape = CircleShape),
                                painter = painterResource(option.country!!.flag),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            Text(option.country.fullName, color = Color.White)
                        }
                    },
                    onClick = {
                        expanded = false
                        onClick(option)
                    }
                )
            }
        }
    }
}
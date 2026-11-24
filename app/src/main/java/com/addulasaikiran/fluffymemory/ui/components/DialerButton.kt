package com.addulasaikiran.fluffymemory.ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addulasaikiran.fluffymemory.ui.theme.LocalDialerColors

enum class DialerButtonStyle {
    PRIMARY,
    SECONDARY,
    DESTRUCTIVE
}

@Composable
fun DialerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: DialerButtonStyle = DialerButtonStyle.PRIMARY
) {
    val colors = LocalDialerColors.current
    when (style) {
        DialerButtonStyle.PRIMARY -> Button(
            onClick = onClick,
            modifier = modifier.defaultMinSize(minHeight = 48.dp),
            enabled = enabled
        ) {
            Text(text = text)
        }
        DialerButtonStyle.SECONDARY -> OutlinedButton(
            onClick = onClick,
            modifier = modifier.defaultMinSize(minHeight = 48.dp),
            enabled = enabled,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(text = text)
        }
        DialerButtonStyle.DESTRUCTIVE -> Button(
            onClick = onClick,
            modifier = modifier.defaultMinSize(minHeight = 48.dp),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.danger,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(text = text)
        }
    }
}

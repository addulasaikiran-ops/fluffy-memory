package com.addulasaikiran.fluffymemory.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.addulasaikiran.fluffymemory.ui.theme.LocalDialerColors

@Composable
fun CallAction(
    label: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    destructive: Boolean = false,
    enabled: Boolean = true
) {
    val dialerColors = LocalDialerColors.current
    val background = when {
        destructive -> dialerColors.danger
        active -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val foreground = when {
        destructive -> MaterialTheme.colorScheme.surface
        active -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier
            .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)
            .semantics {
                contentDescription = label
            }
            .clickable(
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        shape = CircleShape,
        color = background,
        contentColor = foreground,
        enabled = enabled
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            icon()
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

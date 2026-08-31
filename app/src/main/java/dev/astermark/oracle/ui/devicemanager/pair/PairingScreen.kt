package dev.astermark.oracle.ui.devicemanager.pair

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.astermark.oracle.R
import dev.astermark.oracle.ui.theme.OracleTheme

@Composable
fun PairingScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    endpoint: String = "ws://127.0.0.1:8765/hdp/v0/socket",
    onEndpointChange: (String) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Spacer(Modifier.width(11.dp))

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                onClick = onBack,
                                role = Role.Button,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier = Modifier.size(36.dp),
                            painter = painterResource(R.drawable.baseline_chevron_left_24),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "Pair a device",
                        fontSize = 28.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(Modifier.height(22.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White
                )
            }

            Column(
                Modifier
                    .padding(horizontal = 30.dp, vertical = 35.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Bridge endpoint",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(6.dp))

                BasicTextField(
                    value = endpoint,
                    onValueChange = onEndpointChange,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 14.sp),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10))
                        .background(color = Color(0xFF232532), shape = RoundedCornerShape(10))
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Connect this device to Hermes by USB. Pairing continues after you approve the host on this device.",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun PairingScreenPreview() {
    OracleTheme(darkTheme = true) {
        PairingScreen(onBack = {})
    }
}

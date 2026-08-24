package dev.astermark.oracle.ui.devicemanager.pair

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.astermark.oracle.R
import dev.astermark.oracle.ui.theme.OracleTheme

@Composable
fun PairingScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: PairingUiState = PairingUiState.Idle,
    endpoint: String = "ws://10.0.2.2:8765/hdp/v0/socket",
    onEndpointChange: (String) -> Unit = {},
    onPair: (String) -> Unit = {}
) {
    val codeLength = 6
    var code by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

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
                    text = "On the device, run:",
                    color = Color.Gray,
                    fontSize = 24.sp
                )

                Spacer(Modifier.height(25.dp))

                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(10)
                        )
                        .height(55.dp)
                        .fillMaxWidth()
                        .background(color = Color(0xFF232532))
                        .padding(start = 22.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "hdp-bridge pair --new",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp
                    )
                }

                Spacer(Modifier.height(25.dp))

                Text(
                    text = "Enter the code it gives you.",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(14.dp))

                BasicTextField(
                    value = code,
                    onValueChange = { newValue ->
                        val oldText = code.text
                        val newText = newValue.text

                        if (newText.length > oldText.length) {
                            val addedIndex = code.selection.start
                            if (addedIndex < oldText.length) {
                                // Replacement logic: overwrite existing character
                                val updatedText = oldText.take(addedIndex) + 
                                    newText[addedIndex] + 
                                    oldText.drop(addedIndex + 1)
                                
                                code = newValue.copy(
                                    text = updatedText.take(codeLength),
                                    selection = TextRange((addedIndex + 1).coerceAtMost(codeLength))
                                )
                            } else if (newText.length <= codeLength) {
                                code = newValue
                            }
                        } else {
                            code = newValue
                        }

                        if (code.text.length == codeLength && code.selection.start == codeLength) {
                            keyboardController?.hide()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    decorationBox = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    focusRequester.requestFocus()
                                    keyboardController?.show()
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(codeLength) { index ->
                                Box(
                                    modifier = Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        focusRequester.requestFocus()
                                        keyboardController?.show()
                                        val newIndex = index.coerceAtMost(code.text.length)
                                        code = code.copy(selection = TextRange(newIndex))
                                    }
                                ) {
                                    DigitBox(
                                        value = code.text.getOrNull(index)?.toString() ?: "",
                                        isFocused = code.selection.start == index
                                    )
                                }

                                if (index == 2) {
                                    Spacer(Modifier.width(12.dp))
                                    Text(
                                        text = "-",
                                        color = Color.Gray,
                                        fontSize = 24.sp
                                    )
                                    Spacer(Modifier.width(12.dp))
                                } else if (index < codeLength - 1) {
                                    Spacer(Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                )

                Spacer(Modifier.weight(1f))

                val statusText = when (uiState) {
                    is PairingUiState.Failed -> uiState.message
                    PairingUiState.Pairing -> "Pairing…"
                    PairingUiState.Paired -> "Paired."
                    PairingUiState.Idle -> null
                }
                statusText?.let {
                    Text(
                        text = it,
                        color = if (uiState is PairingUiState.Failed) MaterialTheme.colorScheme.error else Color.Gray,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(10.dp))
                }

                Column {
                    PairDeviceButton(
                        enabled = code.text.length == codeLength && uiState !is PairingUiState.Pairing,
                        onClick = { onPair(code.text) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DigitBox(
    value: String,
    isFocused: Boolean
) {
    Box(
        modifier = Modifier
            .width(38.dp)
            .height(48.dp)
            .background(Color(0xFF232532), RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            style = TextStyle(
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun PairDeviceButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = "Add Device",
            fontSize = 11.sp,
            letterSpacing = 2.5.sp,
            fontWeight = FontWeight.Medium
        )
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
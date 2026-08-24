package dev.astermark.oracle.ui.devicemanager.pair

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.astermark.oracle.ui.theme.OracleTheme

@Composable
fun PairingInput(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(5.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            TextField(
                modifier = Modifier
                    .width(28.dp)
                    .height(34.dp),
                singleLine = true,
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(20)
            )

            Spacer(Modifier.width(2.dp))

            TextField(
                modifier = Modifier.width(28.dp).height(34.dp),
                singleLine = true,
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(20)
            )

            Spacer(Modifier.width(2.dp))

            TextField(
                modifier = Modifier.width(28.dp).height(34.dp),
                singleLine = true,
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(20)
            )

            Spacer(Modifier.width(2.dp))

            Text(
                text = "\u2014",
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.width(2.dp))

            TextField(
                modifier = Modifier.width(28.dp).height(34.dp),
                singleLine = true,
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(20)
            )

            Spacer(Modifier.width(2.dp))

            TextField(
                modifier = Modifier.width(28.dp).height(34.dp),
                singleLine = true,
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(20)
            )

            Spacer(Modifier.width(2.dp))

            TextField(
                modifier = Modifier.width(28.dp).height(34.dp),
                singleLine = true,
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(20)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PairingInputPreview() {
    OracleTheme(darkTheme = true) {
        PairingInput(Modifier.background(MaterialTheme.colorScheme.background))
    }
}
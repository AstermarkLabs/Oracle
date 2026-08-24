package dev.astermark.oracle.ui.devicemanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.astermark.hdp_node.NodeState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DeviceManagerScreen(
    nodeState: StateFlow<NodeState>,
    modifier: Modifier = Modifier
) {
    val state by nodeState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = describe(state))
    }
}

private fun describe(state: NodeState): String = when (state) {
    NodeState.Idle -> "Not paired"
    NodeState.Connecting -> "Connecting…"
    is NodeState.Online -> "Online (${state.deviceId})"
    is NodeState.Offline -> "Offline"
    is NodeState.Revoked -> "Revoked — pair again"
    is NodeState.Incompatible -> "Incompatible: ${state.reason}"
}

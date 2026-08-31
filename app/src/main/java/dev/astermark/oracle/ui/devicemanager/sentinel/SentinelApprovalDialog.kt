package dev.astermark.oracle.ui.devicemanager.sentinel

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.astermark.hdp_node.sentinel.SentinelApprovalRequest
import dev.astermark.hdp_node.sentinel.SentinelDecision

/** The primary device's fail-closed approval prompt for one pending secondary enrollment. */
@Composable
fun SentinelApprovalDialog(
    request: SentinelApprovalRequest,
    onDecision: (SentinelDecision) -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDecision(SentinelDecision.DENY) },
        title = { Text("Approve a new device?") },
        text = {
            Text(
                "${request.candidateName} wants to join this Hermes host.\n\n" +
                    "Host: ${request.hostKeyFingerprint}\n" +
                    "Device: ${request.candidateKeyFingerprint}",
            )
        },
        confirmButton = {
            Button(onClick = { onDecision(SentinelDecision.APPROVE) }) { Text("Approve") }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDecision(SentinelDecision.DENY) }) { Text("Deny") }
            OutlinedButton(onClick = { onDecision(SentinelDecision.BLOCK) }) { Text("Block") }
        },
    )
}

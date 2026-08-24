package dev.astermark.oracle.ui.devicemanager.pair

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.astermark.hdp_node.HdpNode
import dev.astermark.hdp_node.NodeState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/** UI-facing pairing outcome. Mirrors [NodeState] but only the subset the pairing screen cares
 * about — it does not need to distinguish "never started" from "backgrounded", for instance. */
sealed interface PairingUiState {
    data object Idle : PairingUiState
    data object Pairing : PairingUiState
    data object Paired : PairingUiState
    data class Failed(val message: String) : PairingUiState
}

/**
 * Drives [HdpNode.pair]. android-node-contract.md: "the node must not retry a rejected code
 * automatically; surface the failure and let the user request a fresh code" — [onPair] refuses a
 * second attempt while one is already in flight, and a failure never re-submits the same code; the
 * user must edit the field and tap the button again.
 */
class PairingViewModel(
    private val hdpNode: HdpNode,
    private val persistEndpoint: suspend (String) -> Unit,
    initialEndpoint: String,
) : ViewModel() {
    var uiState: PairingUiState by mutableStateOf(PairingUiState.Idle)
        private set

    var endpoint: String by mutableStateOf(initialEndpoint)
        private set

    private var awaitingPairResult = false

    init {
        hdpNode.state.onEach(::handleStateChange).launchIn(viewModelScope)
    }

    fun onEndpointChange(value: String) {
        endpoint = value
        viewModelScope.launch { persistEndpoint(value) }
    }

    fun onPair(code: String) {
        if (uiState is PairingUiState.Pairing) return // one attempt in flight at a time
        awaitingPairResult = true
        uiState = PairingUiState.Pairing
        hdpNode.pair(code)
    }

    private fun handleStateChange(state: NodeState) {
        if (!awaitingPairResult) return
        when (state) {
            is NodeState.Online -> {
                uiState = PairingUiState.Paired
                awaitingPairResult = false
            }
            is NodeState.Revoked -> {
                uiState = PairingUiState.Failed("Pairing code rejected. Request a new code and try again.")
                awaitingPairResult = false
            }
            is NodeState.Incompatible -> {
                uiState = PairingUiState.Failed(state.reason)
                awaitingPairResult = false
            }
            is NodeState.Offline -> {
                uiState = PairingUiState.Failed("Could not reach the bridge. Check the endpoint and try again.")
                awaitingPairResult = false
            }
            NodeState.Idle, NodeState.Connecting -> Unit
        }
    }
}

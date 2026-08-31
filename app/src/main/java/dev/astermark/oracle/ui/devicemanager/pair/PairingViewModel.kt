package dev.astermark.oracle.ui.devicemanager.pair

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Owns the editable endpoint while USB bootstrap performs the enrollment authorization. The app
 * never submits a code: enrollment starts only after verified USB approval.
 */
class PairingViewModel(
    private val persistEndpoint: suspend (String) -> Unit,
    initialEndpoint: String,
) : ViewModel() {
    var endpoint: String by mutableStateOf(initialEndpoint)
        private set

    fun onEndpointChange(value: String) {
        endpoint = value
        viewModelScope.launch { persistEndpoint(value) }
    }
}

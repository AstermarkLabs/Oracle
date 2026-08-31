package dev.astermark.oracle

import android.content.Context
import android.os.Build
import dev.astermark.hdp_node.DeviceIdentity
import dev.astermark.hdp_node.HdpNode
import dev.astermark.hdp_node.capability.CapabilityRegistry
import dev.astermark.hdp_node.capability.DeviceStatus
import dev.astermark.hdp_node.capability.NotificationsSend
import dev.astermark.hdp_node.credential.CredentialStore
import dev.astermark.hdp_node.credential.DeviceKeyStore
import dev.astermark.hdp_node.policy.LocalPolicy
import dev.astermark.hdp_node.policy.PermissionGate
import dev.astermark.hdp_node.sentinel.CandidateBootstrapVerifier
import dev.astermark.hdp_node.sentinel.TrustedHostStore
import dev.astermark.hdp_node.transport.HdpSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import kotlin.getValue

/** The M6 emulator-to-host bridge endpoint (android-node-contract.md's "M6 bridge handoff").
 * `10.0.2.2` is the emulator's fixed alias to the host loopback. */
private const val DEFAULT_ENDPOINT = "ws://127.0.0.1:8765/hdp/v0/socket"

/**
 * Hand-rolled composition root for the process (m6-handoff.md §3.2: no DI framework — Hilt is not
 * worth its build cost in this app). One instance lives on [OracleApplication] for the process
 * lifetime.
 */
class AppContainer(private val context: Context) {
    private val nodeScope: CoroutineScope = CoroutineScope(SupervisorJob())

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            // HDP-0 §4's built-in-ping requirement: the spec forbids hand-rolling this.
            .pingInterval(15, TimeUnit.SECONDS)
            .build()
    }

    val deviceIdentity: DeviceIdentity by lazy { DeviceIdentity(context) }
    val credentialStore: CredentialStore by lazy { CredentialStore(context) }
    val deviceKeyStore: DeviceKeyStore by lazy { DeviceKeyStore() }
    val trustedHostStore: TrustedHostStore by lazy { TrustedHostStore(context) }
    val candidateBootstrapVerifier: CandidateBootstrapVerifier by lazy {
        CandidateBootstrapVerifier(deviceKeyStore, trustedHostStore)
    }
    val permissionGate: PermissionGate by lazy { PermissionGate(context) }
    val localPolicy: LocalPolicy by lazy { LocalPolicy() }

    val capabilityRegistry: CapabilityRegistry by lazy {
        CapabilityRegistry(
            listOf(
                NotificationsSend(context),
                DeviceStatus(),
            )
        )
    }

    /** Persisted, user-configurable HDP endpoint. M6 default is the emulator host route.
     * [restoreEndpoint] loads any previously-saved value at process start. */
    @Volatile
    var endpoint: String = DEFAULT_ENDPOINT
        private set

    suspend fun restoreEndpoint() {
        deviceIdentity.currentEndpoint()?.let { endpoint = it }
    }

    suspend fun setEndpoint(url: String) {
        endpoint = url
        deviceIdentity.setEndpoint(url)
    }

    val hdpNode: HdpNode by lazy {
        HdpNode(
            endpoint = { endpoint },
            socket = HdpSocket(okHttpClient),
            credentialStore = credentialStore,
            deviceKeyStore = deviceKeyStore,
            deviceIdentity = deviceIdentity,
            capabilityRegistry = capabilityRegistry,
            localPolicy = localPolicy,
            permissionGate = { permissionGate.canPostNotifications() },
            deviceName = { deviceIdentity.deviceName(defaultDeviceName()) },
            scope = nodeScope,
            candidateBootstrapVerifier = candidateBootstrapVerifier,
        )
    }

    private fun defaultDeviceName(): String = "${Build.MANUFACTURER} ${Build.MODEL}".trim()
}

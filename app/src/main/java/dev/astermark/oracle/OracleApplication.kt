package dev.astermark.oracle

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Application entry point. Owns the single [AppContainer] instance for the process — this app
 * has no DI framework (m6-handoff.md §3.2: Hilt is not worth its build cost here), so
 * [AppContainer] is the one hand-rolled composition root.
 *
 * Foreground-only connection lifecycle (android-node-contract.md, m6-handoff.md §3.2): the node
 * holds its realtime connection only while the app is foregrounded. `ProcessLifecycleOwner`
 * (whole-process foreground/background, not per-Activity) drives [dev.astermark.hdp_node.HdpNode]
 * directly — no foreground service, since one is "not a protocol requirement" absent an
 * explicitly selected persistent-device mode.
 */
class OracleApplication : Application() {
    val container: AppContainer by lazy { AppContainer(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        val processLifecycle = ProcessLifecycleOwner.get().lifecycle
        processLifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                container.hdpNode.start()
            }

            override fun onStop(owner: LifecycleOwner) {
                container.hdpNode.stop()
            }
        })
        ProcessLifecycleOwner.get().lifecycleScope.launch {
            container.restoreEndpoint()
        }
    }
}

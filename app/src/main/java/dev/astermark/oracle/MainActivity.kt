package dev.astermark.oracle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.astermark.oracle.ui.devicemanager.DeviceManagerScreen
import dev.astermark.oracle.ui.devicemanager.pair.PairingScreen
import dev.astermark.oracle.ui.devicemanager.pair.PairingViewModel
import dev.astermark.oracle.ui.navigation.DeviceManager
import dev.astermark.oracle.ui.navigation.LocalNav
import dev.astermark.oracle.ui.navigation.Onboarding
import dev.astermark.oracle.ui.navigation.Pair
import dev.astermark.oracle.ui.navigation.Navigator
import dev.astermark.oracle.ui.navigation.rememberNavigationState
import dev.astermark.oracle.ui.navigation.toEntries
import dev.astermark.oracle.ui.onboarding.OnboardingScreen
import dev.astermark.oracle.ui.onboarding.OnboardingViewModel
import dev.astermark.oracle.ui.onboarding.onboardingPages
import dev.astermark.oracle.ui.theme.OracleTheme

class MainActivity : ComponentActivity() {
    private val container: AppContainer by lazy { (application as OracleApplication).container }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OracleTheme {
                val navigationState = rememberNavigationState(
                    startRoute = Onboarding,
                    topLevelRoutes = setOf(Onboarding, DeviceManager)
                )
                val navigator = remember { Navigator(navigationState) }

                CompositionLocalProvider(LocalNav provides navigator) {
                    val entryProvider = entryProvider {
                        entry<Onboarding> {
                            val onboardingViewModel: OnboardingViewModel = viewModel()
                            OnboardingScreen(
                                currentPage = onboardingViewModel.currentPage,
                                pages = onboardingPages,
                                onContinue = {
                                    onboardingViewModel.nextPage()
                                }
                            )
                        }
                        entry<Pair> {
                            val pairingViewModel: PairingViewModel = viewModel(
                                factory = viewModelFactory {
                                    initializer {
                                        PairingViewModel(
                                            hdpNode = container.hdpNode,
                                            persistEndpoint = { container.setEndpoint(it) },
                                            initialEndpoint = container.endpoint,
                                        )
                                    }
                                }
                            )
                            PairingScreen(
                                onBack = { navigator.goBack() },
                                uiState = pairingViewModel.uiState,
                                endpoint = pairingViewModel.endpoint,
                                onEndpointChange = pairingViewModel::onEndpointChange,
                                onPair = pairingViewModel::onPair
                            )
                        }
                        entry<DeviceManager> {
                            DeviceManagerScreen(nodeState = container.hdpNode.state)
                        }
                    }

                    NavDisplay(
                        entries = navigationState.toEntries(entryProvider),
                        onBack = { navigator.goBack() }
                    )
                }
            }
        }
    }
}

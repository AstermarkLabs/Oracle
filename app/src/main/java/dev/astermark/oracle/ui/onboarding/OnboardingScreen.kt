package dev.astermark.oracle.ui.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import dev.astermark.oracle.ui.navigation.DeviceManager
import dev.astermark.oracle.ui.navigation.LocalNav
import dev.astermark.oracle.ui.navigation.Navigator
import dev.astermark.oracle.ui.navigation.Onboarding
import dev.astermark.oracle.ui.navigation.Pair
import dev.astermark.oracle.ui.navigation.rememberNavigationState
import dev.astermark.oracle.ui.theme.OracleTheme

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun OnboardingScreen(
    currentPage: Int,
    pages: List<OnboardingPage>,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val anchors = mapOf(0f to 0, 1000f to 1)
    val nav = LocalNav.current
    val page = pages[currentPage]
    val swipeableState = rememberSwipeableState(initialValue = 0)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 24.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, bottom = 96.dp)
        ) {
            OnboardingProgress(
                currentPage = currentPage,
                pageCount = pages.size
            )

            Spacer(Modifier.weight(1f))

            OnboardingPageContent(page)

            Spacer(Modifier.weight(1.25f))
        }

        if (currentPage < onboardingPages.lastIndex) {
            OnboardingContinueButton(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                OnboardingConnectButton(
                    onClick = { nav.navigate(Pair)},
                    modifier = Modifier
                        .padding(bottom = 25.dp)
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 50.dp)
                        .clickable(
                            onClick = {},
                            role = Role.Button,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    text = "SKIP FOR NOW",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
private fun OnboardingContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
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
            text = "CONTINUE",
            fontSize = 11.sp,
            letterSpacing = 2.5.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun OnboardingConnectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
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
            text = "CONNECT",
            fontSize = 11.sp,
            letterSpacing = 2.5.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun OnboardingScreenPreview() {
    OracleTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        val navigationState = rememberNavigationState(
            startRoute = Onboarding,
            topLevelRoutes = setOf(Onboarding, DeviceManager)
        )
        val navigator = remember { Navigator(navigationState) }
        val onboardingViewModel: OnboardingViewModel = viewModel()

        CompositionLocalProvider(LocalNav provides navigator) {
            OnboardingScreen(
                currentPage = onboardingViewModel.currentPage,
                pages = onboardingPages,
                onContinue = { onboardingViewModel.nextPage() },
            )
        }
    }
}
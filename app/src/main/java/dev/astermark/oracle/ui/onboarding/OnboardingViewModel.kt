package dev.astermark.oracle.ui.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    var currentPage by mutableIntStateOf(0)
        private set

    fun nextPage() {
        if (currentPage < onboardingPages.lastIndex) {
            currentPage++
        }
    }
}
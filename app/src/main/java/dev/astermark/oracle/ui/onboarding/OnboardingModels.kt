package dev.astermark.oracle.ui.onboarding

data class OnboardingPage(
    val number: String,
    val title: String,
    val desc: String
)

val onboardingPages = listOf(
    OnboardingPage(
        "01",
        "Meet Oracle.",
        "An assistant that reaches past the chat window — into your laptops, servers, and" +
                " tools."
    ),
    OnboardingPage(
        "02",
        "Nothing without your say-so",
        "Every action Oracle takes on a device is checked against permissions you control" +
                " — before it happens, and again on the device itself."
    ),
    OnboardingPage(
        "03",
        "Connect a device.",
        "Pair a laptop or server so Oracle can act on it directly. You can do this anytime" +
                " from Devices."
    )
)
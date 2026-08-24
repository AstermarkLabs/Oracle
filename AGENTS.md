# Repository Guidelines

## Project Structure & Module Organization

Oracle is a single-module Android application. The `app/` module contains the
application code and resources. Kotlin sources live under
`app/src/main/java/dev/astermark/oracle/`; keep feature UI grouped by package,
for example `ui/onboarding/` and `ui/devicemanager/`. Compose theme definitions
belong in `ui/theme/`. Android resources are in `app/src/main/res/`. Put local
unit tests in `app/src/test/` and device/emulator tests in `app/src/androidTest/`.
Versions and dependency aliases are centralized in `gradle/libs.versions.toml`.

## Build, Test, and Development Commands

Run commands from the repository root:

```bash
./gradlew :app:assembleDebug       # compile a debug APK
./gradlew :app:testDebugUnitTest   # run JVM unit tests
./gradlew :app:connectedDebugAndroidTest  # run tests on a connected device/emulator
./gradlew :app:lintDebug           # run Android lint for the debug variant
```

Use Android Studio to run the `app` configuration when iterating on Compose UI.
Do not edit generated build output such as `app/build/` or `.gradle/`.

## Coding Style & Naming Conventions

Write idiomatic Kotlin with four-space indentation and one top-level public
type per file when practical. Use `PascalCase` for classes, composables, and
files (`OnboardingScreen.kt`); use `camelCase` for functions and properties.
Name composables as UI nouns and event callbacks with `on` prefixes, such as
`onContinue`. Keep UI state in `ViewModel` classes and make state mutation
private unless callers must change it. Preserve the package and application ID
`dev.astermark.oracle`.

## Testing Guidelines

Use JUnit 4 for fast logic tests and AndroidX JUnit/Espresso or Compose testing
for Android behavior. Name tests for the observed result, e.g.
`nextPage_advancesUntilLastPage`. Add tests with feature behavior; run the
relevant unit-test task before opening a pull request and connected tests when
changes depend on Android framework or Compose interaction.

## Commit & Pull Request Guidelines

This repository has no commit history yet. Use concise, imperative Conventional
Commit-style subjects, for example `feat: add device pairing flow` or
`fix: prevent onboarding overflow`. Keep commits focused. Pull requests should
explain the user-facing change, list verification performed, link relevant
issues, and include screenshots or a short recording for visual Compose changes.

## Configuration & Security

Keep machine-specific SDK paths in `local.properties`, which is ignored. Never
commit signing keys, keystores, tokens, or secrets. Add new dependencies through
the version catalog rather than hard-coding versions in module build files.

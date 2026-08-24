# Graph Report - Oracle  (2026-08-22)

## Corpus Check
- 25 files · ~5,627 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 95 nodes · 92 edges · 27 communities (16 shown, 11 thin omitted)
- Extraction: 87% EXTRACTED · 13% INFERRED · 0% AMBIGUOUS · INFERRED: 12 edges (avg confidence: 0.78)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- .onCreate
- Repository Guidelines
- OnboardingScreen
- OracleTheme
- OnboardingViewModel
- gradlew
- ExampleInstrumentedTest
- rememberNavigationState
- Android Launcher Icon (HDPI)
- ExampleUnitTest
- NavKey.kt
- PairingScreen
- Android Launcher Icon (MDPI)
- Android Round Launcher Icon (MDPI)
- Android Launcher Icon (XHDPI)
- Android Round Launcher Icon (XHDPI)
- Android Launcher Icon (XXHDPI)
- Android Round Launcher Icon (XXHDPI)
- Android Launcher Icon (XXXHDPI)
- Android Round Launcher Icon (XXXHDPI)
- CLAUDE.md

## God Nodes (most connected - your core abstractions)
1. `OnboardingScreen()` - 9 edges
2. `Repository Guidelines` - 7 edges
3. `PairingScreen()` - 6 edges
4. `OracleTheme()` - 5 edges
5. `rememberNavigationState()` - 4 edges
6. `toEntries()` - 4 edges
7. `Navigator` - 4 edges
8. `OnboardingPageContent()` - 4 edges
9. `MainActivity` - 3 edges
10. `DeviceManagerScreen()` - 3 edges

## Surprising Connections (you probably didn't know these)
- `README` --conceptually_related_to--> `Android Launcher Icon (HDPI)`  [INFERRED]
  README.md → app/src/main/res/mipmap-hdpi/ic_launcher.webp
- `PairingScreenPreview()` --calls--> `OracleTheme()`  [INFERRED]
  app/src/main/java/dev/astermark/oracle/ui/devicemanager/pair/PairingScreen.kt → app/src/main/java/dev/astermark/oracle/ui/theme/Theme.kt
- `OnboardingScreenPreview()` --calls--> `OracleTheme()`  [INFERRED]
  app/src/main/java/dev/astermark/oracle/ui/onboarding/OnboardingScreen.kt → app/src/main/java/dev/astermark/oracle/ui/theme/Theme.kt
- `PairingInputPreview()` --calls--> `OracleTheme()`  [INFERRED]
  app/src/main/java/dev/astermark/oracle/ui/devicemanager/pair/PairingInput.kt → app/src/main/java/dev/astermark/oracle/ui/theme/Theme.kt
- `OnboardingScreen()` --calls--> `OnboardingPageContent()`  [INFERRED]
  app/src/main/java/dev/astermark/oracle/ui/onboarding/OnboardingScreen.kt → app/src/main/java/dev/astermark/oracle/ui/onboarding/OnboardingPageContent.kt

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Android Launcher Icons** — app_src_main_res_mipmap_hdpi_ic_launcher, app_src_main_res_mipmap_hdpi_ic_launcher_round, app_src_main_res_mipmap_mdpi_ic_launcher, app_src_main_res_mipmap_mdpi_ic_launcher_round, app_src_main_res_mipmap_xhdpi_ic_launcher, app_src_main_res_mipmap_xhdpi_ic_launcher_round, app_src_main_res_mipmap_xxhdpi_ic_launcher, app_src_main_res_mipmap_xxhdpi_ic_launcher_round, app_src_main_res_mipmap_xxxhdpi_ic_launcher, app_src_main_res_mipmap_xxxhdpi_ic_launcher_round [EXTRACTED 1.00]

## Communities (27 total, 11 thin omitted)

### Community 0 - ".onCreate"
Cohesion: 0.15
Nodes (7): MainActivity, DeviceManagerScreen(), Modifier, NavKey, Navigator, Bundle, ComponentActivity

### Community 1 - "Repository Guidelines"
Cohesion: 0.25
Nodes (7): Build, Test, and Development Commands, Coding Style & Naming Conventions, Commit & Pull Request Guidelines, Configuration & Security, Project Structure & Module Organization, Repository Guidelines, Testing Guidelines

### Community 2 - "OnboardingScreen"
Cohesion: 0.21
Nodes (10): OnboardingPage, Modifier, OnboardingPageContent(), Modifier, OnboardingProgress(), Modifier, OnboardingConnectButton(), OnboardingContinueButton() (+2 more)

### Community 3 - "OracleTheme"
Cohesion: 0.40
Nodes (4): Modifier, PairingInput(), PairingInputPreview(), OracleTheme()

### Community 5 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 7 - "rememberNavigationState"
Cohesion: 0.43
Nodes (6): NavKey, NavigationState, rememberNavigationState(), toEntries(), NavEntry, SnapshotStateList

### Community 8 - "Android Launcher Icon (HDPI)"
Cohesion: 0.67
Nodes (3): Android Launcher Icon (HDPI), Android Round Launcher Icon (HDPI), README

### Community 11 - "NavKey.kt"
Cohesion: 0.60
Nodes (4): DeviceManager, NavKey, Onboarding, Pair

### Community 12 - "PairingScreen"
Cohesion: 0.60
Nodes (5): DigitBox(), Modifier, PairDeviceButton(), PairingScreen(), PairingScreenPreview()

### Community 25 - "CLAUDE.md"
Cohesion: 0.40
Nodes (4): Commands, Conventions Not Enforced by Tooling, Sensitive Areas, Workflow Rules

## Knowledge Gaps
- **20 isolated node(s):** `Project Structure & Module Organization`, `Build, Test, and Development Commands`, `Coding Style & Naming Conventions`, `Testing Guidelines`, `Commit & Pull Request Guidelines` (+15 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **11 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `OnboardingScreen()` connect `OnboardingScreen` to `.onCreate`?**
  _High betweenness centrality (0.101) - this node is a cross-community bridge._
- **Why does `OracleTheme()` connect `OracleTheme` to `.onCreate`, `OnboardingScreen`, `PairingScreen`?**
  _High betweenness centrality (0.055) - this node is a cross-community bridge._
- **Why does `rememberNavigationState()` connect `rememberNavigationState` to `.onCreate`?**
  _High betweenness centrality (0.054) - this node is a cross-community bridge._
- **Are the 3 inferred relationships involving `OnboardingScreen()` (e.g. with `.onCreate()` and `OnboardingPageContent()`) actually correct?**
  _`OnboardingScreen()` has 3 INFERRED edges - model-reasoned connections that need verification._
- **Are the 4 inferred relationships involving `OracleTheme()` (e.g. with `.onCreate()` and `PairingInputPreview()`) actually correct?**
  _`OracleTheme()` has 4 INFERRED edges - model-reasoned connections that need verification._
- **What connects `Project Structure & Module Organization`, `Build, Test, and Development Commands`, `Coding Style & Naming Conventions` to the rest of the system?**
  _20 weakly-connected nodes found - possible documentation gaps or missing edges._
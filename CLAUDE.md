## Commands
```bash
./gradlew :app:assembleDebug              # debug APK
./gradlew :app:testDebugUnitTest          # JVM unit tests
./gradlew :app:connectedDebugAndroidTest  # device/emulator tests
./gradlew :app:lintDebug                  # lint
```
Use Android Studio for iterating on Compose UI. Never edit `app/build/` or `.gradle/`.

## Architecture: two projects
The HDP/0 protocol node (pairing, credential/key storage, capability execution, invocation
dispatch, reconnect/backoff) is **not** part of this repo. It lives in the sibling project
`../HDPAndroidNode` (module `hdp-node`, package `dev.astermark.hdp_node.*`) and is consumed here
as a published local Maven artifact (`dev.astermark:hdp-node`), not a source/composite-build
dependency. See `../HDPAndroidNode/docs/handoff.md` for that project's own status.

**Workflow implication**: after changing anything in `hdp-node`, you must run
`./gradlew publishToMavenLocal` in `HDPAndroidNode` before Oracle picks it up — Oracle's
`app/build.gradle.kts` disables Gradle's changing-module cache
(`resolutionStrategy.cacheChangingModulesFor(0, "seconds")`) so a rebuild always re-resolves the
SNAPSHOT, but it still has to be republished first. `settings.gradle.kts` adds `mavenLocal()` to
the repository list for this reason.

`AppContainer.kt` remains the composition root and constructs every hdp-node building block
itself (`CredentialStore`, `DeviceKeyStore`, `DeviceIdentity`, `CapabilityRegistry`,
`LocalPolicy`, `PermissionGate`, `HdpSocket`, `HdpNode`, ...) — hdp-node exposes all of these as
public classes rather than a single factory/facade, by design.

## Conventions Not Enforced by Tooling
- Package structure: group feature UI by package under `ui/` (e.g. `ui/onboarding/`, `ui/devicemanager/`); theme in `ui/theme/`.
- One top-level public type per file when practical.
- Event callbacks prefixed `on` (e.g. `onContinue`); composables named as UI nouns.
- UI state lives in `ViewModel`; keep mutation private unless callers must change it.
- Dependency versions go in `gradle/libs.versions.toml`, not hard-coded in module build files.
- Test names describe the observed result (e.g. `nextPage_advancesUntilLastPage`).

## Sensitive Areas
- `applicationId`/package `dev.astermark.oracle` — preserve, don't rename casually.
- Never commit signing keys, keystores, tokens, or secrets. Machine-specific SDK path lives in `local.properties` (gitignored).

## Workflow Rules
- Run relevant unit tests before opening a PR; run connected tests when a change touches Android framework or Compose interaction.
- Commit subjects: concise, imperative, Conventional Commit style (`feat: add device pairing flow`).
- PRs: describe user-facing change, list verification performed, include screenshots/recording for visual Compose changes.

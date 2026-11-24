# System Architecture

**Status:** Proposed design. This document describes the intended architecture; the current repository is still an initial Compose shell and does not yet implement these features.

## 1. Goals

- Provide a modern Android phone experience with Favourites, Recents, Contacts, Keypad, and Settings.
- Integrate with Android Telecom so the app can act as the default phone app.
- Keep personal data on-device by default.
- Separate UI, domain rules, Android platform integration, and persistence so each can be tested.
- Avoid a server dependency for core calling and contact features.

## 2. Technology baseline

| Area | Decision |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| App architecture | MVVM / unidirectional data flow |
| Async work | Kotlin coroutines and Flow |
| Dependency injection | Constructor injection initially; add Hilt only when module complexity warrants it |
| Local database | Room over SQLite |
| Platform integration | Android Telecom, Contacts Provider, Call Log Provider |
| Build | Gradle; GitHub Actions builds the APK |

The repository currently has a single app module. Start with package boundaries inside that module; split into Gradle modules only when build or ownership complexity justifies it.

## 3. Runtime architecture

1. **Presentation:** Compose screens render immutable UI state and send user intents to ViewModels.
2. **Domain:** Use cases coordinate actions such as placing a call, toggling a favourite, and loading recent calls.
3. **Data:** Repository interfaces expose app-owned data and normalized platform data.
4. **Platform adapters:** Android-specific adapters use TelecomManager, RoleManager, ContactsContract, and CallLog.Calls.
5. **Persistence:** Room stores app-owned favourites, preferences, and optional cached display metadata. Android remains the source of truth for contacts and the system call log.

## 4. Key platform decisions

- Request the `ROLE_DIALER` role through Android's role-request flow. Do not silently assume the app is the default dialer.
- Implement an `InCallService` and the required dialer UI before claiming full in-call functionality.
- Use `TelecomManager` / `TelecomManager.placeCall` for outgoing calls when the app is the default dialer and has the required permissions and role.
- Query contacts and call history through Android content providers with runtime permission handling.
- Preserve emergency-call handling through Android's system flow. Never intercept, block, or replace emergency calling behavior.
- Treat Android platform data as potentially unavailable: permissions may be denied, providers may return empty data, and role ownership may change.

## 5. Main feature boundaries

- **Favourites:** app-owned list of contact/number references and ordering.
- **Recents:** read-only presentation of Android call-log entries; do not create a competing call-log database.
- **Contacts:** read-only access to the system Contacts Provider for the initial release.
- **Keypad:** number entry, formatting, contact lookup, and call initiation.
- **In-call:** answer, reject, hang up, mute, speaker, and DTMF where supported by Telecom state/capabilities.
- **Settings:** app-owned preferences, permission/role status, and privacy information.

## 6. Error and lifecycle handling

- Represent permission denial, missing default-dialer role, unavailable contact data, and Telecom failures as explicit UI states.
- Do not perform provider queries on the main thread.
- Collect flows with lifecycle-aware Compose APIs.
- Reconcile call state from Telecom callbacks; never infer that a call connected merely because the user tapped the call button.
- Keep UI state recoverable after process death; persist only durable user choices, not transient call state.

## 7. Delivery phases

1. Navigation shell and design system.
2. Local favourites and settings with Room.
3. Contact and call-log read integration with permission UX.
4. Default-dialer role request and outgoing call integration.
5. In-call service and call controls.
6. Accessibility, privacy review, device testing, and release hardening.

## 8. Explicit non-goals for the first release

No account system, remote API, cloud backup, analytics SDK, advertising SDK, call recording, or contact upload is planned. Any future addition requires a separate privacy and security review.

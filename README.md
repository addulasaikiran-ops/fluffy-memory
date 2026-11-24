# iOS-Inspired Android Dialer

A privacy-focused Android phone and dialer application built with Kotlin and Jetpack Compose. The visual language is inspired by modern iOS design, while all phone functionality uses supported Android APIs.

## Development approach

This repository is the source of truth. The project is edited through GitHub, and GitHub Actions will compile the Android APK in the cloud. Android Studio is not required on the developer's laptop.

## Planned technology

- Kotlin
- Jetpack Compose and Material 3
- MVVM / layered architecture
- Android Contacts and Telecom APIs
- Room for app-owned persistent data
- DataStore for preferences
- GitHub Actions for cloud builds

## Build an APK

1. Open the **Actions** tab in this repository.
2. Select **Android CI**.
3. Run the workflow using **Run workflow**, or push a commit to `main`.
4. Open the completed workflow run.
5. Download the `app-debug-apk` artifact.
6. Transfer the APK to your Android phone and install it. Android may ask you to allow installation from that source.

The first cloud build will download Gradle and Android SDK components and may take several minutes.

## Important calling limitation

Real incoming/outgoing call handling requires Android Telecom integration and, for full dialer replacement, the user must explicitly select this app as the default phone app. Features vary by Android version, device manufacturer, carrier, and permissions. The app will not fake call records or silently place calls.

## Project status

Initial repository setup. The first milestone is a clean, reproducible cloud build. Features will be added in small, tested phases; no feature will be described as functional until it is implemented and verified.

## Privacy

The design goal is local-first storage. Contacts and call-log data must not be uploaded to an external server. Permissions will be requested only when the related feature is used.

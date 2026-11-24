# Security Architecture

**Status:** Proposed security baseline. Verify all permissions, manifest declarations, and platform behavior during implementation and device testing.

## 1. Security objectives

- Keep contacts, call history, and favourites on-device.
- Request only permissions required by implemented features.
- Make role and permission requests understandable and user-initiated.
- Avoid leaking phone numbers or contact data through logs, analytics, network calls, backups, or crash reports.
- Treat provider data and external intents as untrusted input.

## 2. Data classification

| Data | Sensitivity | Handling |
|---|---|---|
| Phone numbers and contact names | High | Read on demand; do not log or upload |
| Call history and timestamps | High | Read from system provider; display only as needed |
| Favourites | High | Store in app-private Room database |
| App preferences | Low to moderate | Store in app-private DataStore |
| Active call state | High | Keep transient; receive from Telecom |

## 3. Permissions and role

- Request `ROLE_DIALER` through Android's supported role API and explain the user-visible benefits.
- Request contacts and call-log permissions only when the related feature is used and only if permitted by current Play policy and Android version.
- Do not request broad storage, location, microphone, or camera permissions for basic dialer features.
- Microphone access for cellular calls is managed by the system dialer/Telecom path; do not add recording or audio-capture features.
- Re-check role and permission state when returning to the app because users can revoke or change defaults in system settings.
- Provide graceful limited-mode behavior after denial.

## 4. Storage and transport

- Keep Room and DataStore files in the app's private internal storage.
- Do not add a network client or INTERNET permission for the first-release feature set.
- Exclude sensitive app data from backup by default; verify backup/data-extraction rules for supported Android versions.
- Do not claim database-level encryption unless an encryption implementation is actually added and reviewed.
- Never store authentication secrets because the initial design has no account or backend.

## 5. Logging and diagnostics

- Never log raw phone numbers, contact names, call timestamps, or call contents.
- Use structured, redacted error messages.
- Disable verbose production logging.
- Crash diagnostics, if introduced, must be reviewed for sensitive-data capture and disclosed to users as appropriate.

## 6. Input and intent safety

- Normalize and validate dial strings before handing them to Telecom.
- Do not execute arbitrary URI schemes from contact/provider fields.
- Validate incoming intents and restrict exported components to those required by Android Telecom or the launcher.
- Use explicit intents for internal navigation where applicable.
- Follow Android manifest requirements for `InCallService`, role qualification, and call UI; do not expose internal components unnecessarily.

## 7. Threats and mitigations

| Threat | Mitigation |
|---|---|
| Accidental contact/call data disclosure | No upload, redacted logs, least-privilege access |
| Malicious or malformed provider values | Validate and safely render all provider data |
| Unauthorized component invocation | Minimal exported surface and intent validation |
| Lost device / app data exposure | App-private storage, OS device protection, backup exclusion |
| Permission or role revoked | Re-check state and fail closed with clear UX |
| Dependency compromise | Pin and update dependencies, review changes, use CI checks |

## 8. Release security checklist

- [ ] Review merged manifest and exported component declarations.
- [ ] Confirm role request uses the system UI.
- [ ] Verify runtime permission rationale and denial paths.
- [ ] Confirm no phone numbers or contacts appear in logs.
- [ ] Verify backup exclusion and device-transfer behavior.
- [ ] Test with role granted, role denied, permissions denied, and permissions revoked.
- [ ] Test incoming, outgoing, rejected, missed, and ended call states on supported devices.
- [ ] Review third-party dependencies and remove unused libraries.
- [ ] Verify emergency calling continues to use Android's system-supported behavior.

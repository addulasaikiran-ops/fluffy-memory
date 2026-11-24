# API Specifications

**Status:** Internal interface contract proposal. There is no remote HTTP API in the first-release architecture.

## 1. API strategy

The application is local-first. Feature code calls Kotlin repository interfaces and use cases. Implementations adapt Android platform APIs and local storage. No server, authentication token, or network permission is required for core dialer behavior.

## 2. Domain models

```kotlin
data class PhoneContact(
    val lookupKey: String,
    val displayName: String,
    val numbers: List<PhoneNumber>
)

data class PhoneNumber(
    val raw: String,
    val normalized: String?,
    val label: String?
)

data class RecentCall(
    val id: Long,
    val number: String?,
    val cachedName: String?,
    val callType: CallType,
    val timestampEpochMs: Long,
    val durationSeconds: Long
)

enum class CallType { INCOMING, OUTGOING, MISSED, REJECTED, UNKNOWN }

data class Favourite(
    val id: String,
    val contactLookupKey: String?,
    val normalizedNumber: String,
    val displayNameOverride: String?,
    val sortOrder: Int
)
```

These are proposed contracts; adapt names and fields to actual provider behavior.

## 3. Repository contracts

```kotlin
interface ContactsRepository {
    fun observeContacts(query: String): kotlinx.coroutines.flow.Flow<List<PhoneContact>>
    suspend fun getContact(lookupKey: String): PhoneContact?
}

interface CallHistoryRepository {
    fun observeRecentCalls(limit: Int): kotlinx.coroutines.flow.Flow<List<RecentCall>>
}

interface FavouritesRepository {
    fun observeFavourites(): kotlinx.coroutines.flow.Flow<List<Favourite>>
    suspend fun addFavourite(contactLookupKey: String?, number: String, label: String? = null)
    suspend fun removeFavourite(id: String)
    suspend fun moveFavourite(id: String, newPosition: Int)
}

interface CallRepository {
    suspend fun placeCall(number: String): PlaceCallResult
}

sealed interface PlaceCallResult {
    data object Started : PlaceCallResult
    data object DefaultDialerRequired : PlaceCallResult
    data object PermissionRequired : PlaceCallResult
    data class Failed(val reason: CallFailure) : PlaceCallResult
}
```

A `Started` result means the platform accepted the request; it does not mean the remote party answered. The in-call UI must observe Telecom state.

## 4. Platform adapter responsibilities

| Adapter | Android API | Responsibility |
|---|---|---|
| DefaultDialerRoleGateway | RoleManager | Check/request `ROLE_DIALER` |
| TelecomCallGateway | TelecomManager | Place outgoing calls |
| InCallServiceAdapter | InCallService | Receive calls and observe/control call objects |
| ContactsProviderDataSource | ContactsContract | Query contacts and phone numbers |
| CallLogDataSource | CallLog.Calls | Query recent call records |

All provider access must run off the main thread and handle permission denial, empty cursors, missing columns, and provider errors.

## 5. Permission and role contract

- Contacts queries require the relevant contacts read permission.
- Call-log access is restricted by Android policy and generally requires the app to qualify as the default phone handler; request only what the shipped feature needs.
- Outgoing call and in-call behavior must follow the active default-dialer role and Android Telecom requirements.
- Request the default dialer role with the system role UI; do not imitate system consent.
- If setup is incomplete, return a typed result and show a route to Settings/setup rather than crashing.

## 6. Error model

| Error | Expected behavior |
|---|---|
| PermissionDenied | Show explanation and retry action |
| DefaultDialerRequired | Offer system role request |
| InvalidNumber | Keep keypad contents and show validation |
| TelecomUnavailable | Show a recoverable error |
| ProviderUnavailable | Show empty/unavailable state; preserve other screens |
| StorageFailure | Show non-destructive error; do not silently discard user changes |

## 7. HTTP API

No HTTP endpoints are defined for the initial release. If a backend is introduced later, document its purpose, data minimization, authentication, transport security, retention, and deletion behavior before implementation.

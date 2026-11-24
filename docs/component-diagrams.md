# Component Diagrams

**Status:** Proposed component boundaries; implementation is pending.

## 1. Application component diagram

```mermaid
flowchart TB
    User[User]
    subgraph UI[Presentation - Jetpack Compose]
      Nav[App Navigation]
      FavUI[Favourites Screen]
      RecUI[Recents Screen]
      ConUI[Contacts Screen]
      KeyUI[Keypad Screen]
      CallUI[In-call Screen]
      SetUI[Settings Screen]
      VM[ViewModels / UI State]
    end
    subgraph Domain[Domain Layer]
      UC[Use Cases]
      Models[Domain Models]
      RepoContracts[Repository Interfaces]
    end
    subgraph Data[Data Layer]
      FavRepo[Favourites Repository]
      ContactsRepo[Contacts Repository]
      CallsRepo[Call History Repository]
      SettingsRepo[Settings Repository]
    end
    subgraph Platform[Android Platform]
      Telecom[TelecomManager / InCallService]
      ContactsProvider[Contacts Provider]
      CallLogProvider[Call Log Provider]
      Permissions[RoleManager / Runtime Permissions]
    end
    subgraph Local[Local Storage]
      Room[(Room Database)]
      Prefs[(DataStore Preferences)]
    end
    User --> Nav
    Nav --> FavUI
    Nav --> RecUI
    Nav --> ConUI
    Nav --> KeyUI
    Nav --> CallUI
    Nav --> SetUI
    FavUI --> VM
    RecUI --> VM
    ConUI --> VM
    KeyUI --> VM
    CallUI --> VM
    SetUI --> VM
    VM --> UC
    UC --> Models
    UC --> RepoContracts
    RepoContracts --> FavRepo
    RepoContracts --> ContactsRepo
    RepoContracts --> CallsRepo
    RepoContracts --> SettingsRepo
    FavRepo --> Room
    FavRepo --> ContactsProvider
    ContactsRepo --> ContactsProvider
    CallsRepo --> CallLogProvider
    KeyUI --> Telecom
    CallUI --> Telecom
    Permissions --> Telecom
    Permissions --> ContactsProvider
    Permissions --> CallLogProvider
    SettingsRepo --> Prefs
```

## 2. Call placement sequence

```mermaid
sequenceDiagram
    actor User
    participant Keypad as Keypad UI
    participant VM as KeypadViewModel
    participant UseCase as PlaceCallUseCase
    participant Role as Role/Permission Gateway
    participant Telecom as Android Telecom
    participant Service as App InCallService
    participant CallUI as In-call UI

    User->>Keypad: Tap Call
    Keypad->>VM: PlaceCall(number)
    VM->>UseCase: execute(number)
    UseCase->>Role: Check default dialer role + call permission
    alt Role or permission missing
      Role-->>UseCase: Not ready
      UseCase-->>VM: NeedsSetup
      VM-->>Keypad: Show setup guidance
    else Ready
      Role-->>UseCase: Ready
      UseCase->>Telecom: placeCall(number, extras)
      Telecom-->>UseCase: Request accepted / error
      UseCase-->>VM: Call request result
      Telecom->>Service: onCallAdded(call)
      Service->>CallUI: Publish call state
      CallUI-->>User: Render actual call state and controls
    end
```

## 3. Data ownership

| Data | Owner / source | App behavior |
|---|---|---|
| Contacts | Android Contacts Provider | Read on demand; do not duplicate full address book |
| Call history | Android Call Log Provider | Read and display; system owns records |
| Favourites | App Room database | Store contact lookup reference or normalized number |
| Preferences | DataStore | Store theme and user-facing preferences |
| Active call state | Android Telecom | Observe callbacks; keep transient UI state in memory |

## 4. Package layout proposal

```text
com.addulasaikiran.fluffymemory
├── app/                 # Application entry point and navigation
├── core/
│   ├── model/           # Shared domain models
│   ├── ui/              # Theme and reusable Compose components
│   └── common/          # Dispatchers, result types, utilities
├── feature/
│   ├── favourites/
│   ├── recents/
│   ├── contacts/
│   ├── keypad/
│   ├── incall/
│   └── settings/
├── domain/              # Use cases and repository contracts
├── data/
│   ├── local/           # Room and DataStore
│   └── repository/      # Repository implementations
└── platform/
    ├── telecom/
    ├── contacts/
    └── calllog/
```

This is a target package layout, not a claim that these packages already exist.

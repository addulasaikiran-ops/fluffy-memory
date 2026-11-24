# Wireframes and Screen Layouts

**Status:** Low-fidelity wireframe specification. High-fidelity visual screens are a separate design deliverable and have not been approved yet.

## Shared application frame

- Status bar follows system appearance.
- Screen content uses a consistent 20dp horizontal margin.
- Main destinations use a persistent bottom navigation bar.
- Main screen title aligns consistently across tabs.
- In-call experience is full-screen and independent from the main tab bar.

## Favourites

```text
┌────────────────────────────────┐
│ Favourites                 [+] │
│ Your people, one tap away      │
│                                │
│ [Avatar] Name        [Call]    │
│          Number                │
│                                │
│ [Avatar] Name        [Call]    │
│          Number                │
│                                │
│                                │
│ ★       ◷       ◎      ⌗    ⚙ │
└────────────────────────────────┘
```

Layout notes: optional grid for a small number of favourites; switch to a list for larger collections. Add/reorder actions must remain discoverable.

## Recents

```text
┌────────────────────────────────┐
│ Recents                    [⌕] │
│ [All] [Missed]                 │
│ Today                          │
│ [Avatar] Name          10:42   │
│          Incoming · 2 min      │
│ [Avatar] Name          09:18   │
│          Outgoing              │
│ [Avatar] Number        08:03   │
│          Missed                │
│                                │
│ ★       ◷       ◎      ⌗    ⚙ │
└────────────────────────────────┘
```

Layout notes: group by date; visually distinguish missed calls with icon and label. Row tap opens details; call action is separately accessible.

## Contacts

```text
┌────────────────────────────────┐
│ Contacts                   [⌕] │
│ [ Search names or numbers... ] │
│ A                              │
│ [Avatar] Ananya Sharma         │
│ [Avatar] Arun Kumar            │
│ S                              │
│ [Avatar] Sandeep Kumar         │
│                                │
│ ★       ◷       ◎      ⌗    ⚙ │
└────────────────────────────────┘
```

Layout notes: alphabetical sections, sticky letter headers where practical, search results update as the user types.

## Keypad

```text
┌────────────────────────────────┐
│ Keypad                         │
│                                │
│          +91 98765 43210       │
│                         [×]    │
│                                │
│       [1]     [2]     [3]      │
│               ABC     DEF      │
│       [4]     [5]     [6]      │
│       GHI     JKL     MNO      │
│       [7]     [8]     [9]      │
│       PQRS    TUV     WXYZ     │
│       [*]     [0]     [#]      │
│                                │
│              [Call]            │
│                                │
│ ★       ◷       ◎      ⌗    ⚙ │
└────────────────────────────────┘
```

Layout notes: keep keypad centered and reachable; call action remains visually dominant. Delete supports a clear tap target and long-press repeat.

## Settings

```text
┌────────────────────────────────┐
│ Settings                       │
│ Appearance                     │
│ Theme              System   >  │
│ Haptics              On    >   │
│                                │
│ Calling                        │
│ Default phone app       Ready  │
│ Permissions             Manage │
│                                │
│ Privacy                        │
│ On-device data                 │
│ About                          │
│                                │
│ ★       ◷       ◎      ⌗    ⚙ │
└────────────────────────────────┘
```

Layout notes: group settings by purpose; show current default-dialer role status and provide a clear system settings action.

## Contact details

- Large avatar and display name.
- Phone numbers as distinct tappable rows with labels.
- Primary call action.
- Add/remove favourite action.
- Avoid exposing unrelated contact fields unless needed.

## In-call

- Contact identity and number near the top/center.
- Call status and duration are driven by Telecom.
- Grid of Mute, Keypad, Speaker, and other supported actions.
- Large End call action, separated from other controls.
- Incoming state offers Answer and Decline; active state offers End call.

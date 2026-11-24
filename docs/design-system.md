# Design System

**Status:** Proposed visual language and tokens. Values are starting points for review, not yet wired into Compose.

## 1. Visual direction

A refined, minimal dialer: warm neutral surfaces, deep ink text, a confident blue action color, softly rounded surfaces, and restrained shadows. The interface should feel familiar on Android while borrowing the visual calm and polish associated with premium phone interfaces.

## 2. Color tokens

### Light theme

| Token | Hex | Usage |
|---|---|---|
| background | #F7F8FA | App canvas |
| surface | #FFFFFF | Cards, sheets, navigation |
| surface-subtle | #EEF1F5 | Secondary containers |
| text-primary | #151922 | Main text |
| text-secondary | #687181 | Supporting text |
| accent | #2563EB | Primary action and selected state |
| accent-soft | #E7EEFF | Selected chips and subtle accent |
| success | #16845B | Positive status |
| danger | #D92D3F | End call, destructive actions |
| divider | #E4E7EC | Separators |

### Dark theme

| Token | Hex | Usage |
|---|---|---|
| background | #0D1118 | App canvas |
| surface | #151B24 | Cards, sheets, navigation |
| surface-subtle | #202938 | Secondary containers |
| text-primary | #F4F6FA | Main text |
| text-secondary | #A5AFBE | Supporting text |
| accent | #82A9FF | Primary action and selected state |
| accent-soft | #1C3158 | Selected chips and subtle accent |
| success | #55C79A | Positive status |
| danger | #FF6476 | End call, destructive actions |
| divider | #2A3442 | Separators |

Do not use color as the only signal for call type, selection, or errors. Pair it with labels and icons.

## 3. Typography

Use **Roboto** as the initial Android-native font family. Prefer system font scaling and avoid fixed-height text containers.

| Style | Size | Weight | Typical use |
|---|---:|---|---|
| Display | 32sp | Semibold | Large keypad number |
| Headline | 28sp | Semibold | Screen title |
| Title | 20sp | Semibold | Contact name / section title |
| Body | 16sp | Regular | Main row content |
| Body small | 14sp | Regular | Supporting details |
| Label | 12sp | Medium | Tabs, chips, metadata |
| Button | 16sp | Semibold | Primary actions |

Use tabular numerals for phone numbers and call durations where supported. Keep line height comfortable and allow user font scaling.

## 4. Shape, spacing, and elevation

- Base spacing unit: 4dp.
- Screen horizontal inset: 20dp (adjust for compact widths).
- Standard list row: 68–76dp depending on secondary content.
- Card corner radius: 20dp.
- Button corner radius: 18dp; circular controls remain circular.
- Search field radius: 16dp.
- Prefer tonal surface separation over heavy drop shadows.
- Use elevation sparingly and consistently.

## 5. Component rules

### Buttons
- Primary: accent fill, high-contrast label/icon.
- Secondary: surface-subtle fill or outlined treatment.
- Destructive: danger treatment, especially End call.
- Disabled: reduced emphasis but readable and clearly non-interactive.

### Contact avatar
- Circular shape.
- Photo when supplied by Contacts Provider; otherwise initials.
- Initials fallback must be deterministic and accessible.

### Navigation
- Five destinations: Favourites, Recents, Contacts, Keypad, Settings.
- Selected destination uses accent icon and label; do not rely on icon color alone.
- Keep labels visible to aid discoverability.

### Search
- Search icon, descriptive hint, clear action.
- Search by contact name or phone number.
- Preserve query when opening a contact and returning to results.

### Call controls
- Large, clearly labeled actions.
- End call uses danger token.
- Active toggles such as Mute and Speaker need both visual and accessibility state.
- Button state must reflect Telecom capability and call state.

## 6. Light and dark theme behavior

- Follow Android system theme by default.
- Offer explicit Light, Dark, and System choices in Settings.
- Apply theme immediately without restarting.
- Keep semantic tokens consistent; do not simply invert colors.
- Check surfaces, dividers, icons, disabled states, text, and dialogs in both themes.
- Respect system contrast and font-scale settings where practical.

## 7. Accessibility requirements

- Minimum 48dp touch targets.
- WCAG-inspired contrast targets: 4.5:1 for normal text and 3:1 for large text and meaningful UI graphics.
- Provide content descriptions for icon-only controls.
- Use semantic roles and selected/checked state.
- Respect font scaling and display size.
- Support TalkBack and switch navigation.
- Pair missed/incoming/outgoing status icons with text labels.

## 8. Compose implementation mapping

When implementation begins, centralize values in a theme package:
- `Color.kt`: light and dark semantic palettes
- `Type.kt`: typography scale
- `Shape.kt`: corner shapes
- `Theme.kt`: system/light/dark selection
- Reusable composables: app button, contact avatar, call action, search field, list row, bottom navigation

Do not scatter raw hex colors or one-off spacing values through feature screens.

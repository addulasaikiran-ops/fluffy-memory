# Reusable Compose UI Components

Package: `com.addulasaikiran.fluffymemory.ui.components`

These components provide presentation primitives for feature screens. They do not access Contacts Provider, Telecom, or other Android platform services; callers supply display data and callbacks.

## Components

- `DialerButton`: primary, secondary, and destructive text buttons. Destructive styling uses the semantic danger token.
- `ContactAvatar`: circular contact photo when a Painter is supplied, otherwise deterministic initials from the display name.
- `ContactRow`: 72dp-minimum contact row with avatar, name, supporting text, click callback, and optional trailing content.
- `DialerSearchField`: controlled search input. The caller owns and preserves the query; the clear affordance sends an empty string to the callback.
- `CallAction`: labeled circular call-control surface with active and destructive states. The caller supplies the icon composable and owns the enabled/active state.

## Usage notes

- Wrap screens in `DialerTheme` so Material and dialer semantic tokens follow the selected light/dark/system palette.
- Keep state and platform behavior in the calling feature/ViewModel; these composables only render state and invoke callbacks.
- Supply meaningful labels and icons for call controls. Do not use color alone to communicate active or destructive state.
- Use `ContactAvatar` with a photo Painter only after the feature layer has loaded an image from an approved source.
- Search query persistence and contact filtering belong to the screen/ViewModel, not the text field.

## Accessibility and sizing

- Buttons and call actions expose interactive targets with at least 48dp minimum dimensions.
- Contact rows are at least 72dp high.
- Icon content in `CallAction` is paired with a visible text label and a semantic description.
- Search clear action has a descriptive content description.
- Contact avatars expose the contact name to accessibility services; decorative photo content is not separately announced.
- Components use Material theme colors and the semantic `LocalDialerColors` palette, so both themes are supported.

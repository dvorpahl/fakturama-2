# Fakturama SSO bridge for PrestaShop 9.2

This module lets a Fakturama workstation open a linked product in the PrestaShop back office as a configured service employee.

## Security flow

Fakturama signs the product ID, issue time, and a random nonce with the shared 256-bit HMAC key. The module accepts requests for 90 seconds, verifies the signature, stores a hash of the nonce to reject replay, then authenticates the configured active employee through PrestaShop's Symfony authenticator manager. It issues a fresh PrestaShop product editor token and redirects to the product editor. The shared key is encrypted in PrestaShop configuration using the shop cookie key and stored in Eclipse Secure Storage on each Fakturama workstation.

The browser session is a dedicated service employee session. Create a separate employee account and grant only the product permissions needed by the team. Audit entries in PrestaShop are attributed to that account.

## Install and configure

1. Upload `fakturamaso.zip` from **Modules > Module Manager** in PrestaShop 9.2 and install it. For an existing 1.0.0 or 1.0.1 install, upload this 1.0.2 package and use the offered update action; do not uninstall first, because uninstalling deletes the shared key and employee setting.
2. Create an active PrestaShop employee for Fakturama with the required product permissions. Do not use the SuperAdmin account.
3. In the module configuration, enter that employee's numeric ID and save. Copy the displayed 64-character shared key.
4. In Fakturama, open **Preferences > Webshop**, set the PrestaShop admin base URL, and paste the shared key into **PrestaShop SSO-Schlüssel**. Save the preferences on each workstation.
5. If the module was already installed, clear the PrestaShop cache under **Advanced Parameters > Performance** after updating. Open a product already linked to a PrestaShop product. **Im Webshop bearbeiten…** should open the editor without a user login or the CSRF safety interstitial.

Rotate the shared key in the module configuration if it is exposed. Paste the new key into every Fakturama workstation after rotating; old links will stop working immediately.

## Compatibility and operational notes

- Requires PrestaShop 9.2 and HTTPS.
- The module uses the `admin_products_edit` route and `UserTokenManager` to generate the per-session `_token`.
- Handoff URLs are single-use, time-limited, and must not be copied for later use.
- Uninstalling the module removes its replay-protection table and configuration values.
- Install and test on a staging shop before production use. The shop must allow a module controller route to create a session through its configured `main` security firewall.

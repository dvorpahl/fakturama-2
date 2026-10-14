package com.sebulli.fakturama.preferences;

import java.io.IOException;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;

/** Stores the PrestaShop handoff key in the workstation's Eclipse Secure Storage. */
public final class PrestaShopSsoSecretStore {
    private static final String NODE = "com.sebulli.fakturama.rcp/prestashop-sso";
    private static final String SECRET = "shared-secret";

    private PrestaShopSsoSecretStore() {
    }

    public static String read() {
        try {
            return SecurePreferencesFactory.getDefault().node(NODE).get(SECRET, "");
        } catch (StorageException e) {
            throw new IllegalStateException("Cannot read PrestaShop key from Eclipse Secure Storage", e);
        }
    }

    public static void write(final String value) {
        final ISecurePreferences preferences = SecurePreferencesFactory.getDefault().node(NODE);
        try {
            if (value == null || value.trim().isEmpty()) {
                preferences.remove(SECRET);
            } else {
                preferences.put(SECRET, value.trim(), true);
            }
            preferences.flush();
        } catch (StorageException | IOException e) {
            throw new IllegalStateException("Cannot save PrestaShop key to Eclipse Secure Storage", e);
        }
    }
}

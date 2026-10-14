package com.sebulli.fakturama.parts;

import java.net.URI;

/** Builds the PrestaShop 9.2.0 back-office edit URL for a product. */
final class PrestaShopProductLink {
    private final URI adminRoot;
    private final long productId;

    PrestaShopProductLink(final String configuredAdminUrl, final long productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Missing PrestaShop product ID");
        }
        final URI configured = URI.create(configuredAdminUrl.trim());
        if (!("https".equalsIgnoreCase(configured.getScheme()) || "http".equalsIgnoreCase(configured.getScheme()))
                || configured.getHost() == null || configured.getRawUserInfo() != null
                || configured.getRawQuery() != null || configured.getRawFragment() != null) {
            throw new IllegalArgumentException("Invalid PrestaShop admin URL");
        }
        String path = configured.getRawPath();
        if (path.endsWith("/index.php")) {
            path = path.substring(0, path.length() - "/index.php".length());
        }
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        adminRoot = URI.create(configured.getScheme() + "://" + configured.getRawAuthority() + path);
        this.productId = productId;
    }

    String adminEditorUrl() {
        return adminRoot + "/index.php/sell/catalog/products/" + productId + "/edit";
    }
}

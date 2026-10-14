package com.sebulli.fakturama.parts;

import java.net.URI;
import java.util.Locale;

/** URLs for an existing WooCommerce product, based on Fakturama's shop URL. */
final class WooCommerceProductLink {
    private final URI shopRoot;
    private final long productId;

    WooCommerceProductLink(final String configuredUrl, final long productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Missing WooCommerce product ID");
        }
        final URI configured = URI.create(configuredUrl.trim());
        if (!("https".equalsIgnoreCase(configured.getScheme()) || "http".equalsIgnoreCase(configured.getScheme()))
                || configured.getHost() == null || configured.getRawUserInfo() != null
                || "shop.fakturama.info".equals(configured.getHost().toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("Invalid WooCommerce URL");
        }
        String path = configured.getRawPath();
        final int wordpressAdminPath = path.indexOf("/wp-admin/");
        final int legacyConnectorPath = path.indexOf("/admin/fakturama2_connector");
        if (wordpressAdminPath >= 0) {
            path = path.substring(0, wordpressAdminPath);
        } else if (legacyConnectorPath >= 0) {
            path = path.substring(0, legacyConnectorPath);
        } else if (path.endsWith(".php")) {
            path = path.substring(0, path.lastIndexOf('/'));
        }
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        shopRoot = URI.create(configured.getScheme() + "://" + configured.getRawAuthority() + path);
        this.productId = productId;
    }

    URI publishedProductUri() {
        return URI.create(shopRoot + "/wp-json/wc/store/v1/products/" + productId);
    }

    String adminEditorUrl() {
        return shopRoot + "/wp-admin/post.php?post=" + productId + "&action=edit";
    }
}

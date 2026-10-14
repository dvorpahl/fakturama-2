package com.sebulli.fakturama.parts;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HexFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/** Builds short-lived, signed PrestaShop 9.2 SSO handoff links. */
final class PrestaShopProductLink {
    private static final SecureRandom RANDOM = new SecureRandom();
    private final URI adminRoot;
    private final long productId;

    PrestaShopProductLink(final String configuredAdminUrl, final long productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Missing PrestaShop product ID");
        }
        final URI configured = URI.create(configuredAdminUrl.trim());
        if (!"https".equalsIgnoreCase(configured.getScheme())
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

    String handoffUrl(final String sharedSecret) {
        final byte[] key;
        try {
            key = HexFormat.of().parseHex(sharedSecret.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid PrestaShop SSO secret", e);
        }
        if (key.length < 32) {
            throw new IllegalArgumentException("PrestaShop SSO secret must contain at least 32 bytes");
        }

        final long issuedAt = Instant.now().getEpochSecond();
        final byte[] nonceBytes = new byte[16];
        RANDOM.nextBytes(nonceBytes);
        final String nonce = HexFormat.of().formatHex(nonceBytes);
        final String canonical = "v1\n" + productId + "\n" + issuedAt + "\n" + nonce;
        try {
            final Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(key, "HmacSHA256"));
            final String signature = HexFormat.of().formatHex(hmac.doFinal(canonical.getBytes(StandardCharsets.UTF_8)));
            return adminRoot + "/modules/fakturamaso/handoff?productId=" + productId
                    + "&iat=" + issuedAt
                    + "&nonce=" + encode(nonce)
                    + "&signature=" + encode(signature);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot sign PrestaShop SSO handoff", e);
        }
    }

    private static String encode(final String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}

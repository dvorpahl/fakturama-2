package com.sebulli.fakturama.views.datatable.products;

/** Three states for the product list's symbol-column filters. */
public enum ProductIconFilter {
    ALL,
    ONLY,
    EXCLUDE;

    public ProductIconFilter next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public static ProductIconFilter fromPreference(final String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ALL;
        }
    }
}

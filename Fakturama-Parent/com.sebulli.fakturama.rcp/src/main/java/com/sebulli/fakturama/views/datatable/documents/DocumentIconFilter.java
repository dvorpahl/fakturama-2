package com.sebulli.fakturama.views.datatable.documents;

/** Three states for the document list's symbol-column filters. */
public enum DocumentIconFilter {
    ALL, ONLY, EXCLUDE;

    public DocumentIconFilter next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public static DocumentIconFilter fromPreference(final String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ALL;
        }
    }
}

# Fakturama agent instructions

## Localized UI strings

When adding or changing UI labels:

1. Add the key to `com.sebulli.fakturama.rcp/OSGI-INF/l10n/bundle.properties` and provide a German translation in `bundle_de.properties`. Add other translations only when they can be translated confidently.
2. If the caller uses `Messages#getMessageFromKey`, add the matching public field to `com.sebulli.fakturama.common/src/main/java/com/sebulli/fakturama/i18n/Messages.java`. That method resolves keys by reflection; a bundle entry alone is not enough. For example, `product.list.field.salesprice` maps to `productListFieldSalesprice`.
3. Check the exact key-to-field mapping and verify that `getMessageFromKey` will find the field. A missing field makes the UI show the key wrapped in question marks.

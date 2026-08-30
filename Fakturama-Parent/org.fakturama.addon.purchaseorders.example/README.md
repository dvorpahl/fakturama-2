# Purchase-orders example add-on

This independent OSGi bundle demonstrates Fakturama's
`org.fakturama.wizards.importWizards` extension point after the provider-bundle
class-loading fix.

It reads the two tables maintained by the sibling `fakturama-tool` project:

- `PART_BESTELLUNG` becomes a `PurchaseOrder` aggregate.
- `PART_BESTELLPOSITION` becomes an immutable `PurchaseOrderItem` and is attached
  through `FK_BESTELLUNG`.
- The four database status values are validated by `PurchaseOrderStatus`.

The add-on is deliberately read-only. It uses native projections through
Fakturama's configured `EntityManagerFactory`, because the core JPA persistence
unit has a fixed entity list and must not be modified by a separately installed
bundle.

## Build

From `Fakturama-Parent`:

```sh
mvn -pl org.fakturama.addon.purchaseorders.example,org.fakturama.addon.purchaseorders.example.feature -am -DskipTests package
```

The resulting bundle and feature archives are placed below the respective
module's `target/` directory. The feature is also assigned to the
`Fakturama Add-ons` category in `com.sebulli.fakturama.site/category.xml`.

After installation, open Fakturama's import dialog and choose
`Database add-ons > Purchase orders (example add-on)` (German UI:
`Datenbank-Add-ons > Warenbestellungen (Beispiel-Addon)`). The configured schema
must contain the tables documented in `../../../fakturama-tool/README.md` (relative
to this module).

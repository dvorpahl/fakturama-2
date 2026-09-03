Analyze and fully implement an explicit product-level setting in this Fakturama 2 project that determines whether inventory is tracked for a product.

# GOAL

In the product editor, add a checkbox directly next to the “Stock quantity” field:

```
[✓] Track inventory
```

This property belongs directly to the product, not to the product category. Product categories must not determine inventory behavior through either their names or their hierarchy.

# FUNCTIONAL RULES

1. Add a positive property to the product model:

   ```
   stockManaged
   ```

   Use a boolean type that fits the existing model conventions and permanently prevent an undefined database state.

2. The existing global setting “Use quantity information (stock quantity)” remains the master switch.

3. The existing global selection of the stock-posting event—order, delivery note, or invoice—must remain unchanged.

4. A stock quantity may only be changed when all of the following conditions are met:

   * global inventory management is enabled,
   * the configured stock-posting event has occurred,
   * the document item references a real product,
   * the product is `stockManaged`,
   * a numeric stock quantity exists.

5. Products that are not stock-managed must still be usable normally in documents. Item quantity, quantity unit, price calculation, and invoice printing must remain unchanged. Only the product’s stock quantity must not be modified.

6. The `stockManaged` flag is authoritative. After migration, do not continue using `quantity == null` as the actual indicator that a product is not stock-managed.

# DATA MIGRATION

Create a Liquibase changeset following the existing project conventions.

Classify existing products during migration as follows:

```
QUANTITY IS NOT NULL  → stockManaged = true
QUANTITY IS NULL      → stockManaged = false
```

New products should be stock-managed by default when the global inventory feature is used.

After the migration has completed, `stockManaged` must not be `NULL` at the database level.

Consider all supported databases:

* HSQLDB
* MySQL
* MariaDB

# MODEL

The product model is generated from `model.ecore` and the existing model artifacts. Modify the authoritative model source and update all generated classes and metamodel artifacts consistently.

Do not modify only a Java file marked `@generated` without also updating the model source.

In particular, inspect and update where required:

* `Product`
* the model factory and generated property/metamodel data
* clone and copy functions
* semantic comparison methods
* product CSV import and export
* migration of legacy Fakturama data
* webshop imports

For existing products, webshop imports must not unintentionally overwrite the locally configured inventory-tracking decision.

# PRODUCT EDITOR

Add a “Track inventory” checkbox next to the “Stock quantity” field in `ProductEditor`.

Required behavior:

* When selected, the stock quantity field is editable.
* When cleared, the stock quantity field is disabled.
* Clearing the checkbox must not automatically delete an existing stock quantity.
* When the checkbox is selected again, the existing value must become visible and editable again.
* If the global “Use stock quantity” feature is disabled, both the checkbox and the stock quantity field must follow the editor’s existing visibility behavior.
* Use the existing Eclipse Data Binding infrastructure.
* Follow the existing dirty-state conventions.
* Add at least English and German translations.
* Do not invent translations for languages you do not confidently understand.

# CENTRAL STOCK UPDATE LOGIC

The actual stock calculation is implemented in `StockUpdateHandler`.

Add the product-level check there as a central rule so that it applies to all existing stock update triggers, including:

* document printing or document generation
* changing an order’s status to `SHIPPED`
* reverting the `SHIPPED` status
* deleting or reversing a document
* invoices, delivery notes, and credit notes, wherever they are already supported by the existing configuration

Do not duplicate the product-level check across all callers.

Callers may continue to determine the event and the quantity difference. The final decision on whether a product’s stock may be changed must be made centrally in `StockUpdateHandler`.

# OUT OF SCOPE

The following are explicitly not part of this task:

* replacing the current print-based stock-posting behavior with a new posting model
* using product categories as an inventory flag
* introducing a new stock movement or inventory journal table
* fixing unrelated printing or stock-management bugs unless they are directly caused by this change
* broadly redesigning the product editor
* requiring WindowBuilder

# HISTORICAL BEHAVIOR

For this task, use the product’s current `stockManaged` state at the time of a stock change.

A historical snapshot of inventory relevance in `DocumentItem`, or an inventory movement journal, is not part of this change.

Explicitly document the following consequence in the final report:

If a product is changed to “not stock-managed” after an earlier stock posting, later corrections, reversals, or deletions of old documents will no longer restore stock for that product.

Do not silently change or work around this behavior.

# TESTS AND VERIFICATION

Add focused tests wherever feasible for at least the following cases:

1. A stock-managed product is correctly reduced when the configured posting event occurs.
2. A product that is not stock-managed remains unchanged during the same event.
3. A document containing mixed items changes stock only for stock-managed products.
4. No stock change occurs when global inventory management is disabled.
5. The `stockManaged` setting is persisted and displayed correctly after reopening the product.
6. The migration correctly classifies existing products based on `QUANTITY`.
7. Clone, copy, and product import paths preserve or handle `stockManaged` correctly.

After implementation, run the relevant module tests and an appropriate Maven/Tycho build.

Do not modify functionally unrelated files. Preserve all existing user changes in the working tree.

# WORKING APPROACH

First inspect the actual model, database, data-binding, stock update, migration, and import paths in this repository.

Then:

1. Provide a short implementation plan.
2. Implement the change completely.
3. Run the relevant tests and build verification.
4. Review the final diff for unintended changes.

Do not invent a new data model or architecture. Adapt the implementation to the existing Fakturama codebase and its established conventions.

If model generation cannot be executed in the local environment, document the exact reason. Even in that case, keep the authoritative model source and all generated artifacts consistently and traceably updated.

# FINAL REPORT

At the end, report:

* which files and model areas were changed,
* how the database migration works,
* where the central `stockManaged` check is implemented,
* which tests and builds were executed and their results,
* any verification that could not be completed,
* the known historical limitation described above.

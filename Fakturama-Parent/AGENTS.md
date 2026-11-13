# Repository Guidelines

## Project Structure

Fakturama is a Java 17 application built on Eclipse e4 and Tycho. The root `pom.xml` defines the multi-module build. Main application code is in `com.sebulli.fakturama.rcp`; shared model and database migrations are in `com.sebulli.fakturama.model/src/main`; integrations and exporters live in modules such as `org.fakturama.connectors`, `org.fakturama.import`, and `org.fakturama.exporter.zugferd`. Automated tests are in the `*.test` modules under `src/test/java`. Product packaging and platform installers are maintained in `com.sebulli.fakturama.site`.

## Build and Test

- `mvn clean verify` builds the Maven/Tycho reactor and runs configured verification.
- `mvn -pl com.sebulli.fakturama.model.test -am test` builds the model test module and its required dependencies, then runs its tests.
- For focused work, use `mvn -pl <module> -am verify` (for example, `-pl com.sebulli.fakturama.rcp`).
- `./build-release.sh` creates release packages and expects local packaging tools and configured output directories; it is not a routine development build.

## Code and Naming

Follow the surrounding Java style: four-space indentation, descriptive Java class and method names, and existing package/module conventions. Keep changes scoped to the relevant OSGi bundle. Do not add independent copies of shared configuration, translations, or layout values.

For web UI work, first inspect comparable pages and shared layout components. Reuse global content widths, page spacing, and responsive breakpoints; change those values only when specifically requested.

## UI Text and Database Changes

For UI labels, add keys to `com.sebulli.fakturama.rcp/OSGI-INF/l10n/bundle.properties` and German translations to `bundle_de.properties`. When using `Messages#getMessageFromKey`, add and verify the matching public field in `com.sebulli.fakturama.common/.../i18n/Messages.java`.

Before schema edits, inspect the actual tables and Liquibase changelogs. Debitors and creditors share `FKT_CONTACT` and are distinguished by `DTYPE`; document receiver snapshots use `FKT_DOCUMENTRECEIVER`. Provide migrations that work for both existing databases and fresh installs.

## Commits and Pull Requests

Use a concise, descriptive commit subject; repository history has no strict prefix convention. Pull requests should explain the user-visible change, list affected modules, link related issues when available, and include screenshots for visual changes. State the build or tests run and note any known gaps.

## Scope Discipline

Keep changes limited to the requested behavior and files. Do not refactor or alter adjacent build, packaging, or release workflows unless explicitly requested. Do not introduce new tool or command dependencies into scripts without checking that the target environment provides them.

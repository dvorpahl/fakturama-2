# Repository Guidelines

## Project Structure & Module Organization

Fakturama is a Java desktop invoicing application built on Eclipse e4, OSGi, and Maven/Tycho. The root reactor contains `Fakturama-Parent/` and the standalone migration tool `org.fakturama.migtool/`.

Within `Fakturama-Parent/`:

- `com.sebulli.fakturama.rcp/` contains the application UI; `com.sebulli.fakturama.common/` provides shared utilities.
- `com.sebulli.fakturama.model/` contains model code and Liquibase changelogs under `src/main/resources/changelog/`.
- `org.fakturama.import/`, `org.fakturama.export/`, and connector bundles implement integrations.
- `com.sebulli.fakturama.resources*` holds shared assets, icons, and templates; `com.sebulli.fakturama.site/` packages products.
- Test bundles use `src/test/java/` and, where needed, `src/test/resources/`.

Consult `Fakturama-Parent/AGENTS.md` for additional instructions when modifying that subtree.

## Development Environment & Shared Database

- Develop and maintain the related `fakturama-tool` Litestar server exclusively on LXC 202 (`dev@192.168.1.202`, SSH host `devtest`) through VS Code Remote-SSH.
- This Java client repository stays on the desktop at `~/Workspace/fakturama-2`. LXC 202 has neither Java nor Maven. The tool's `CLAUDE.md` references `../fakturama-2` for entities and Liquibase changelogs.
- The client and tool share MariaDB on LXC 202: `jdbc:mariadb://192.168.1.202:3307/fakturama_local` (in use since 2026-10-08).
- Coordinate changes to entities such as `Product.java` and `db.changelog-*.xml` with `fakturama-tool` beforehand: they affect the schema expected by the tool and its tests. Execute DDL against `FKT_*` tables only with explicit approval.
- Additional test systems are LXC 201 (WooCommerce), LXC 203 (Shopware), and LXC 204 (PrestaShop v9.2.0, confirmed by the user on 2026-10-14).

## Build, Test, and Development Commands

Use Maven 3.9+; source targets Java 17, while CI uses JDK 21. Run these commands from the repository root:

- `mvn -f Fakturama-Parent/pom.xml clean verify` builds and verifies the application reactor.
- `mvn -f Fakturama-Parent/pom.xml -pl com.sebulli.fakturama.model.test -am verify` runs focused model verification with required modules.
- `mvn -f Fakturama-Parent/pom.xml clean install -DskipTests=true` matches the GitLab packaging build; it skips tests.

For interactive development, import the bundles into Eclipse PDE, activate the target definition in `org.fakturama.target/`, and use the platform launch configurations under `com.sebulli.fakturama.rcp/project/`.

## Coding Style & Naming Conventions

Follow surrounding Java formatting, generally four-space indentation. Use PascalCase classes, camelCase methods and fields, and existing package prefixes. Preserve OSGi bundle boundaries and local formatting; no repository-wide formatter configuration was found. Keep generated `target/` and `bin/` output out of changes.

For web UI changes, reuse existing global widths, spacing, and breakpoints. Change global layout values only on explicit request.

## Testing Guidelines

Tests use JUnit, including Jupiter, through Tycho Surefire. Name classes `*Test.java` and cover changed behavior. The model tests participate in the reactor; common and RCP test modules are currently commented out, so default verification does not execute them. No enforced coverage percentage is configured. Report tests run and any exclusions.

## Commit & Pull Request Guidelines

History uses short descriptive subjects in English and German without a consistent prefix convention. Keep commits focused. Pull requests should explain behavior changes, identify affected bundles, link relevant issues, and record validation. Include screenshots for visual changes and migration details for schema changes.

## Release Documentation

Whenever preparing a release with `package-release`, update `CHANGELOG-desktop.md` to reflect the desktop changes included in that release. Keep the changelog current as part of release preparation.

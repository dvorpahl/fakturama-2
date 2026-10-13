# CLAUDE.md

Hinweise für Claude Code in diesem Repository (Fakturama-Java-Client, Fork `dvorpahl/fakturama-2`).

## Entwicklungsumgebung (seit 2026-10-08)

Das zugehörige Projekt `fakturama-tool` (Litestar-Webserver) wird **ausschließlich auf dem LXC 202**
(`dev@192.168.1.202`, VS Code Remote-SSH, SSH-Host `devtest`) entwickelt und gepflegt. Dessen Datenbank
(MariaDB, Port 3307, `fakturama_local`) nutzt auch der Fakturama-Client.

- Dieses Repo liegt weiterhin nur auf dem Desktop (`~/Workspace/fakturama-2`). Auf der LXC gibt es weder Java noch
  Maven, und `fakturama-tool` verweist in seiner `CLAUDE.md` auf `../fakturama-2` für Entitäten und Liquibase-Changelogs.
- Änderungen an Entitäten (z. B. `Product.java`) oder an `db.changelog-*.xml` ändern das Datenbankschema, das
  `fakturama-tool` und die Tests voraussetzen. Vorher dort abstimmen; DDL auf `FKT_*` nur mit ausdrücklicher Freigabe.
- Der Fakturama-Client spricht seit 2026-10-08 die Datenbank auf der LXC an (`jdbc:mariadb://192.168.1.202:3307/fakturama_local`).
- Weitere Testsysteme: LXC 201 (WooCommerce), 203 (Shopware), 204 (PrestaShop).

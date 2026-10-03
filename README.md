# crpto-notifier (Crypto Notifier)

Experimentelles Java-Projekt (2018, Repo 2021): Ein kleiner Jetty/Jersey-Dienst, der Kerzendaten von Krypto-Boersen (Binance, Poloniex) laedt, daraus einen EMA-Indikator berechnet und Kauf-/Verkaufsvorschlaege ("Suggestions") ueber eine REST-API ausgibt.

**Status: archiviert, nicht mehr gepflegt.** Kein Anlageberatungs- oder Handelsbot-Produkt.

## Voraussetzungen
- JDK 8, Maven

## Build und Start
```
mvn package
```
Die Hauptklasse ist `cryptodealer.Service` (Jetty auf Port 31337). Endpunkte: `/api/v1/currencies`, `/api/v1/suggestions`.

Hinweis: Der Name des Repos enthaelt einen Tippfehler (crpto statt crypto).

# Weather Check

Applicazione desktop Java Swing per consultare il meteo corrente partendo da una selezione su mappa, con supporto a tema, lingua e preferenze utente persistite su file locale.

## Obiettivi del progetto

- Mostrare meteo corrente per una posizione selezionata su mappa.
- Separare chiaramente UI, logica applicativa e integrazioni esterne.
- Consentire estensione a provider meteo/geocoding alternativi.
- Mantenere l'app localizzabile (`it`/`en`) e tematizzabile (`Light`/`Dark`).

## Stack tecnologico

- Java 21
- Gradle
- Swing
- FlatLaf
- MigLayout
- JXMapViewer2
- Jackson (JSON)
- SLF4J + Logback
- JUnit 5, Mockito, AssertJ

## Architettura

Il progetto adotta una struttura **feature-first** con organizzazione interna **layer-first**.

- `core/`: componenti trasversali condivisi
- `features/`: funzionalita applicative organizzate per dominio
- ogni feature separa `views`, `controllers`, `models`, `services`, ecc.

### Struttura delle cartelle

```text
src/
  main/
    java/com/weathercheck/
      app/                     # bootstrap applicazione e frame principale
      core/
        config/                # settings e repository di persistenza
        controls/              # componenti UI base condivisi
        http/                  # client HTTP/JSON astratti e implementazioni
        i18n/                  # gestione lingua/localizzazione
        theme/                 # gestione tema e stili
        units/                 # risoluzione sistema unita per locale
      features/
        home/
          controllers/         # coordinamento eventi della home
          models/              # stato della home
          services/            # logica applicativa della home
          views/               # pannelli Swing della home
        map/
          models/
          services/
          views/
        settings/
          controllers/
          models/
          services/
          views/
        weather/
          mappers/             # mapping codici meteo/DTO
          models/              # modelli dominio meteo/geolocalizzazione
          providers/           # adapter verso provider esterni (Open-Meteo)
          services/            # orchestrazione recupero meteo
    resources/
      com/weathercheck/themes/ # file di tema FlatLaf
      i18n/                    # bundle traduzioni
      logback.xml              # configurazione logging
  test/
    java/com/weathercheck/     # unit test
```

## Flusso funzionale (alto livello)

1. L'utente seleziona una posizione sulla mappa.
2. La feature `weather` risolve geolocalizzazione/meteo tramite provider.
3. La UI aggiorna pin e dettagli (descrizione meteo, temperatura).
4. Le preferenze (tema, lingua, impostazioni) sono persistite su file locale.

## Prerequisiti

- JDK 21 installato e disponibile nel `PATH`
- Gradle installato nel sistema

Nota: nel repository non e presente il Gradle Wrapper (`gradlew`), quindi i comandi sotto usano `gradle`.

## Build, test ed esecuzione

Dal root del progetto:

```bash
gradle clean build
```

Eseguire solo i test:

```bash
gradle test
```

Avviare l'app desktop:

```bash
gradle run
```

## Esecuzione nel browser con CheerpJ

1. Genera il JAR completo di dipendenze e copialo nella cartella `cheerpj/`:

```bash
gradle prepareCheerpj
```

2. Avvia un web server statico dalla root del progetto:

```bash
python3 -m http.server 8080
```

3. Apri nel browser:

```text
http://localhost:8080/cheerpj/
```

La pagina `cheerpj/index.html` usa CheerpJ e avvia direttamente `cheerpj/app.jar` tramite il `Main-Class` nel manifest.

## Configurazione utente locale

All'avvio, le impostazioni vengono lette/salvate in:

```text
~/.weather-check/settings.json
```

Se il file non esiste, vengono applicati default sensati e creato automaticamente al salvataggio.

## Internazionalizzazione e temi

- Lingue supportate tramite resource bundle in `src/main/resources/i18n/`
- Temi FlatLaf configurati in `src/main/resources/com/weathercheck/themes/`
- Risoluzione unita (metrico/imperiale) basata su lingua/locale selezionata

## Qualita del codice

Comandi consigliati prima di aprire una PR:

```bash
gradle test
```

Facoltativo (build completa):

```bash
gradle clean build
```

## Roadmap sintetica

- Estensione a provider meteo multipli
- Miglioramento gestione stati di errore/retry lato UI
- Packaging desktop (es. `jpackage`)

## Licenza

Questo progetto e distribuito con licenza MIT. Vedi il file [LICENSE](LICENSE) per il testo completo.

## Copyright

Copyright (c) 2026 Giuseppe Novielli  
Email: giuseppe.novielli@hrcoffee.it  
GitHub: https://github.com/giuseppenovielli

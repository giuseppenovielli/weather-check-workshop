# Piano Implementativo — weather-check (Java Swing, feature-first + layer-first)

## 1) Summary
Realizzare una desktop app Swing con 2 schermate (`Home`, `Settings`) per meteo corrente da mappa, mantenendo robustezza tecnica ed estendibilità provider.

Vincolo architetturale: struttura `feature-first` con organizzazione interna `layer-first` in ogni feature (`views`, `controllers`, `models`, `services`, ecc.).

## 2) Key Changes (architettura e comportamento)
- Struttura progetto:
  - `core/` (concern trasversali): `i18n`, `theme`, `config`, `http`, `logging`, `common-ui`
  - `features/home/`, `features/settings/`, `features/weather/`, `features/map/`
  - Ogni feature segue layer-first interno:
    - `views/` (Swing panels/dialog/UI composition)
    - `controllers/` (event handling, coordinamento UI-use case)
    - `models/` (state view-model + domain models di feature)
    - `services/` (use case di feature e orchestration)
    - `repositories/` (quando serve persistenza/accesso dati)
    - `mappers/` (DTO↔domain)
    - `components/` (widget riusabili della feature)
- Provider meteo sostituibile:
  - `WeatherProvider` interfaccia, `OpenMeteoWeatherProvider` implementazione v1
  - `WeatherService` in feature weather per orchestrare fetch/metadati
- Mappatura `weather_code`:
  - Catalogo WMO centralizzato (chiavi semantiche) + label localizzata via `ResourceBundle`
  - Fonte: <https://open-meteo.com/en/docs>
- Home:
  - mappa full-page (`JXMapViewer2`)
  - click posizione -> pin con località + pulsante `Download`
  - `Download` -> meteo corrente nel pin (nome meteo + temperatura)
  - footer: `© Giuseppe Novielli · Open‑Meteo`
- Settings:
  - cambio lingua software
  - cambio località utente (ricerca città/geocoding, persistenza)
  - cambio tema `Light/Dark`
  - unità visualizzate derivate dalla lingua/locale impostata
- Timezone/i18n:
  - timezone API sincronizzato con timezone software
  - formati data/ora/numero centralizzati e locale-aware

## 3) API/Interfacce principali
- `WeatherProvider`, `GeocodingProvider`
- `SettingsRepository`
- `ThemeManager`, `I18nManager`, `UnitSystemResolver`
- DTO Open‑Meteo + mapper dedicati
- Convenzione obbligatoria: ogni nuova feature deve rispettare cartelle layer-first interne

## 4) Test Plan
- Unit:
  - mapping `weather_code`
  - resolver unità da locale
  - parsing JSON provider e gestione errori
  - persistenza settings con fallback default
- Service/controller:
  - orchestrazione download meteo, retry/error states
  - cambio lingua/tema/località runtime
- UI smoke:
  - click mappa -> pin -> download -> render meteo
- Build:
  - Gradle `test/check`, packaging `jpackage`

## 5) Assunzioni e default
- Open‑Meteo v1 senza API key.
- Testi UI iniziali `it/en` con fallback `en`; formati internazionali sempre in base al locale selezionato.
- UI pronta a schermi piccoli (responsiva desktop), non mobile nativa.

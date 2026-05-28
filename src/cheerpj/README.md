# CheerpJ Runtime (src/cheerpj)

Questa cartella contiene gli asset necessari per eseguire l'app Java nel browser tramite CheerpJ.

## Requisiti tecnici

- Un browser moderno con supporto JavaScript attivo.
- I file JAR richiesti presenti in questa cartella:
  - `app.jar`
  - `SwingHTMLBrowser.jar`
- Il file di bootstrap HTML:
  - `index.html`
- Un server HTTP statico per servire i file (evitare apertura diretta con `file://`).

## Note operative

- Verificare che i nomi dei file JAR referenziati in `index.html` corrispondano ai file reali presenti in `src/cheerpj`.
- In caso di aggiornamento dell'applicazione Java, rigenerare e sostituire `app.jar`.

## Repository di riferimento

Per dettagli, esempi e metadati ufficiali di integrazione CheerpJ, consultare:

- https://github.com/leaningtech/cheerpj-meta

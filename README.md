# Gestionale Hotel – Web App Full Stack (Java + React)

Questa è un'applicazione web full-stack per la **gestione di un hotel**, sviluppata con **Spring Boot** per il backend e **React** per il frontend.

## Funzionalità principali

- **Gestione prenotazioni**: creazione, modifica, visualizzazione e annullamento
- **Check-in e check-out** con aggiornamento automatico del soggiorno
- **Visualizzazione planning** in stile tableau con camere e occupazione (prenotazioni e soggiorni attivi)
- **Scheda camera** per la gestione del conto, addebiti, storni, trasferimenti e documenti fiscali
- **Gestione utenti e ruoli** con autenticazione tramite JWT
- **Creazione rapida prenotazione** direttamente da cella del planning
- **Interfaccia utente dinamica e responsive**

## Stack tecnologico

- **Frontend**: React, Axios, Bootstrap
- **Backend**: Spring Boot, Spring Security (JWT), JPA/Hibernate
- **Database**: H2 (in-memory) / MySQL (opzionale)
- **Build tools**: Maven, npm

##  Struttura del progetto

- `frontend/`: interfaccia React con pagine per prenotazioni, planning, schede soggiorno, login
- `backend/`: REST API Spring con controller, repository, DTO

##  Refactoring necessario

Attualmente, **la logica di business è contenuta principalmente nei controller** del backend. Per rendere l'applicazione più manutenibile e scalabile è **previsto un refactoring completo** che includerà:

- **Introduzione di un layer `Service`** per separare responsabilità e centralizzare la logica di business
- **Riorganizzazione dei controller** rendendoli più snelli e orientati alle richieste HTTP
- **Pulizia del codice** e adozione di best practice di architettura Spring

##  Miglioramenti futuri

- Miglioramento dello **stile grafico** e dell'**usabilità dell'interfaccia** (UI/UX)
- Introduzione di **notifiche più chiare** e **feedback visivi**
- Ottimizzazione del planning per gestire lunghi periodi
- Implementazione posizione camere vista giardino - vista mare - vista piscina

## Come avviare il progetto

### 1. Backend

```bash
cd backend
./mvnw spring-boot:run

cd frontend
npm install
npm start

Admin: gestione completa

Utente: accesso con permessi limitati

JWT token è gestito via HTTP headers. Login via /login e registrazione via /register.

Note
Alcune funzionalità sono simulate o in fase di sviluppo (es. stampa documenti fiscali).

Il layout è stato pensato per essere estendibile e modularizzato nel tempo.
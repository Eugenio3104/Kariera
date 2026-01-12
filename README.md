# Kariera
Piattaforma web per studenti universitari che permette di gestire il piano di studi, visualizzare il libretto con calcolo automatico della media, monitorare i progressi attraverso una dashboard con grafici statistici, e simulare il voto di laurea.
## Tech Stack
- **Backend**: Spring Boot 3.4.1, Java 17, PostgreSQL
- **Frontend**: Angular 20.3.0, TypeScript
- **Grafici**: Chart.js 4.5.1 + ng2-charts 8.0.0
- **Security**: Spring Security
## Setup
### 1. Database (PostgreSQL)
Crea il database:
```sql
CREATE DATABASE kariera_db;
Configura le credenziali in 
kariera-api/src/main/resources/application.properties
:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/kariera-db
spring.datasource.username=tuo_username
spring.datasource.password=tua_password
Le tabelle verranno create automaticamente da Flyway al primo avvio.
```
2. Avviare il Backend
```back
bash
cd kariera-api
mvn clean install
mvn spring-boot:run
Server disponibile su: http://localhost:8080
```
3. Avviare il Frontend

```front
bash
cd kariera-api/kariera-frontend
npm install
npm start
Applicazione disponibile su: http://localhost:4200
```
Video integrativo del sistema dove si mostrano la maggior parte delle funzionalità : 


https://github.com/user-attachments/assets/3e44f71e-bfd1-4571-b7c6-d4bb507e397f



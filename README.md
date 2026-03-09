# Football Pairs Final Project

This Spring Boot MVC application solves the final assignment:

> Find the pair of players who have played together in the same matches for the longest total time, and show the time played together in each match.

It uses:
- Java basics
- OOP
- SQL database with Spring Data JPA
- Spring MVC + Thymeleaf
- CSV import without external CSV libraries
- layered architecture

## 1. Project idea

The application imports 4 CSV files:

- `teams.csv`
- `players.csv`
- `matches.csv`
- `records.csv`

Then it stores everything in an SQL database and calculates:

1. which two players have the biggest total overlap in minutes
2. how many minutes they overlapped in every common match
3. a full ranking of all pairs

## 2. Core algorithm

For each match:

1. take all participation records for that match
2. compare every pair of players
3. calculate overlap:
   - `start = max(player1.fromMinute, player2.fromMinute)`
   - `end = min(player1.toMinute, player2.toMinute)`
   - `shared = max(0, end - start)`
4. add the overlap to the total minutes for that pair
5. keep per-match details for the UI

### Important rule
If `toMinutes = NULL`, it is treated as `90`.

## 3. Architecture

- `model` - JPA entities
- `repository` - Spring Data repositories
- `service` - business logic and CSV import
- `controller` - MVC controllers
- `dto` - result models for the report
- `util` - helper utilities for parsing

## 4. Database

This project uses **H2** for simplicity.

Why H2:
- SQL database
- easy to run
- no external installation required
- perfect for exam/demo purposes

You can also switch to MySQL later by changing `application.properties` and dependencies.

## 5. CSV import

No external CSV library is used.

The project reads CSV files with:
- `BufferedReader`
- `Files.newBufferedReader(...)`
- custom split logic that supports quoted values

The default CSV path is:
`src/main/resources/data/`

## 6. Features

### Required
- import CSV data
- support multiple date formats
- treat `NULL` end minute as `90`
- store data in database
- calculate player pair overlap
- display top pair and all pairs

### Bonus
- CRUD for Teams
- CRUD for Players
- CRUD for Matches

## 7. Pages

- `/` - home page
- `/analysis` - full ranking and top pair
- `/teams` - team CRUD
- `/players` - player CRUD
- `/matches` - match CRUD
- `/h2-console` - database console

## 8. How to run

### Option A - IntelliJ
1. Open the project in IntelliJ.
2. Wait for Maven import.
3. Run `FootballPairsApplication`.
4. Open:
   - `http://localhost:8080`
   - `http://localhost:8080/analysis`

### Option B - terminal
```bash
mvn spring-boot:run
```

## 9. H2 database access

- URL: `jdbc:h2:mem:footballpairsdb`
- Username: `sa`
- Password: *(empty)*

## 10. Step-by-step learning order

If you want to study the project properly, read it in this order:

1. `model`
2. `repository`
3. `util`
4. `service/CsvImportService`
5. `service/PairAnalysisService`
6. `controller`
7. `templates`

## 11. Possible improvements

- file upload from browser
- REST API version
- export report to CSV/PDF
- pagination for big datasets
- tests for overlap logic

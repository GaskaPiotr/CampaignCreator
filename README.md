# Campaign Management Backend & Frontend

A REST API for managing seller product campaigns with fund deduction and settlement from an Emerald account.

## Tech Stack

### Backend

- **Java:** 25
- **Framework:** Spring Boot 4
- **Database:** H2 Database (in-memory)
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Testing:** JUnit 5, Mockito, AssertJ

### Frontend
- **Framework:** Angular (Standalone Components, Signals)
- **Language:** TypeScript
- **Styling:** CSS
- **Form Controls:** HTML5 `<datalist>` for keyword typeahead

---

## Business Rules

1. **Campaign Funds:**
   - Creating a campaign deducts funds from the Emerald account.
   - Deleting a campaign refunds the entire fund back to the account.
   - Updating a campaign recalculates the difference: increases deduct additional funds, reductions refund the surplus.
2. **Keywords:**
   - Mandatory; must be selected exclusively from the pre-populated dictionary (sorted alphabetically).
3. **Towns:**
   - Optional; selected from the pre-populated list of towns (sorted alphabetically).

---

## Getting Started

### Prerequisites
- JDK 25+
- Node.js (v24+) & npm

### Build and Run Backend

Run using the Maven wrapper:

```bash
cd campaignapi
./mvnw spring-boot:run
```

*(On Windows use `mvnw.cmd spring-boot:run`)*

- **Base API URL:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI Schema:** `http://localhost:8080/v3/api-docs`

### Running Backend Tests

```bash
cd campaignapi
./mvnw clean test
```

### Build and Run Frontend

In a separate terminal:

```bash
cd frontend
npm install
npm start
```

- **Application URL:** `http://localhost:4200`
- API calls (`/api/*`) are proxied to `http://localhost:8080` via `proxy.conf.json`.

---

## REST Endpoints Overview

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/campaigns` | Get all campaigns |
| `GET` | `/api/campaigns/{id}` | Get campaign by ID |
| `POST` | `/api/campaigns` | Create campaign (`201 Created` with `Location` header) |
| `PUT` | `/api/campaigns/{id}` | Update campaign and balance |
| `DELETE` | `/api/campaigns/{id}` | Delete campaign and refund balance (`204 No Content`) |
| `GET` | `/api/campaigns/statuses` | Get available campaign statuses |
| `GET` | `/api/towns` | Get alphabetical list of towns |
| `GET` | `/api/keywords` | Get keyword dictionary |
| `GET` | `/api/emerald-account` | Get current emerald account balance |
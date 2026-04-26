# Pismo Technical Assessment

REST API developed with Java 17 and Spring Boot 3 for account and transaction management.

## Tech Stack

- Java 17
- Spring Boot 3.3
- PostgreSQL 15
- Docker / Docker Compose

## Requirements

- Docker
- Docker Compose

## Running the project

**1. Clone the repository and navigate to the project folder**

**2. Create the `.env` file based on the example:**
```bash
cp .env.example .env
```

**3. Start the containers:**
```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/accounts` | Create a new account |
| GET | `/accounts/{id}` | Get account by ID |
| POST | `/transactions` | Create a new transaction |

## Operation Types

| ID | Description | Accepted Amount |
|----|-------------|-----------------|
| 1 | PURCHASE | Negative only |
| 2 | INSTALLMENT PURCHASE | Negative only |
| 3 | WITHDRAWAL | Negative only |
| 4 | PAYMENT | Positive only |

## Testing

A shell script is provided for quick endpoint testing:

```bash
chmod +x test.sh
./test.sh
```

The script will prompt you for the following values:

| Prompt | Example | Rule |
|--------|---------|------|
| Document number | `12345678901` | Exactly 11 digits |
| Account ID | `1` | Must be an existing account |
| Operation type ID | `1`, `2`, `3` or `4` | See operation types table above |
| Amount | `-50.00` or `50.00` | Types 1, 2, 3 → negative \| Type 4 → positive |

The script runs three requests in sequence: POST /accounts → GET /accounts/{id} → POST /transactions.

### Test cases

> **Important:** before testing any request that uses `accountId`, make sure to create an account first and use the returned `accountId`.

**Valid account creation**
```bash
curl -X POST http://localhost:8080/accounts \
  -H "Content-Type: application/json" \
  -d '{"documentNumber": "12345678901"}'
```

**Invalid document number (not 11 digits)**
```bash
curl -X POST http://localhost:8080/accounts \
  -H "Content-Type: application/json" \
  -d '{"documentNumber": "123"}'
```

**Duplicate document number**
```bash
curl -X POST http://localhost:8080/accounts \
  -H "Content-Type: application/json" \
  -d '{"documentNumber": "12345678901"}'
```

**Account not found**
```bash
curl http://localhost:8080/accounts/999
```

**Valid transaction (PURCHASE — negative amount)**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 1, "amount": -50.00}'
```

**Valid transaction (PAYMENT — positive amount)**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 4, "amount": 50.00}'
```

**Transaction with invalid account**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 999, "operationTypeId": 1, "amount": -50.00}'
```

**Transaction with invalid operation type**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 99, "amount": -50.00}'
```

**Transaction with invalid amount for operation type (types 1, 2, 3 require negative)**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 1, "amount": 50.00}'
```

**Transaction with invalid amount for operation type (type 4 requires positive)**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 4, "amount": -50.00}'
```

## API Documentation

Swagger UI available at: `http://localhost:8080/swagger-ui.html`

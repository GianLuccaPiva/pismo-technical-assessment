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
| 1 | PURCHASE | Positive only |
| 2 | INSTALLMENT PURCHASE | Negative only |
| 3 | WITHDRAWAL | Negative only |
| 4 | PAYMENT | Negative only |

## Testing

A shell script is provided for quick endpoint testing:

```bash
chmod +x test.sh
./test.sh
```

Edit the variables at the top of `test.sh` to customize the test data.

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

**Valid transaction**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 1, "amount": 50.00}'
```

**Transaction with invalid account**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 999, "operationTypeId": 1, "amount": 50.00}'
```

**Transaction with invalid operation type**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 99, "amount": 50.00}'
```

**Transaction with invalid amount for operation type (type 1 requires positive)**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 1, "amount": -50.00}'
```

**Transaction with invalid amount for operation type (types 2, 3, 4 require negative)**
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 2, "amount": 50.00}'
```

## API Documentation

Swagger UI available at: `http://localhost:8080/swagger-ui.html`

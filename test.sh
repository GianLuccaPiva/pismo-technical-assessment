echo "=== Criando conta ==="
curl -X POST http://localhost:8080/accounts \
  -H "Content-Type: application/json" \
  -d '{"documentNumber": "12345678901"}'

echo "\n\n=== Buscando conta por ID ==="
curl http://localhost:8080/accounts/1

echo "\n\n=== Criando transação ==="
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "operationTypeId": 1, "amount": 50.00}'
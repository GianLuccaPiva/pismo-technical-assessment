#!/bin/bash

# ==========================================
# Configurações — altere aqui para seus testes
BASE_URL="http://localhost:8080"
DOCUMENT_NUMBER="12345678901"
ACCOUNT_ID=1
OPERATION_TYPE_ID=1
AMOUNT=-50.00
# ==========================================

SEPARATOR="=================================================="

printf "\n%s\n" "$SEPARATOR"
printf " POST /accounts - Criando conta\n"
printf "%s\n" "$SEPARATOR"
curl -s -X POST "$BASE_URL/accounts" \
  -H "Content-Type: application/json" \
  -d "{\"documentNumber\": \"$DOCUMENT_NUMBER\"}"

printf "\n\n%s\n" "$SEPARATOR"
printf " GET /accounts/$ACCOUNT_ID - Buscando conta por ID\n"
printf "%s\n" "$SEPARATOR"
curl -s "$BASE_URL/accounts/$ACCOUNT_ID"

printf "\n\n%s\n" "$SEPARATOR"
printf " POST /transactions - Criando transação\n"
printf "%s\n" "$SEPARATOR"
curl -s -X POST "$BASE_URL/transactions" \
  -H "Content-Type: application/json" \
  -d "{\"accountId\": $ACCOUNT_ID, \"operationTypeId\": $OPERATION_TYPE_ID, \"amount\": $AMOUNT}"

printf "\n"

#!/bin/bash

BASE_URL="http://localhost:8080"
SEPARATOR="=================================================="

printf "\n%s\n" "$SEPARATOR"
printf " Pismo API - Test Runner\n"
printf "%s\n\n" "$SEPARATOR"

read -p "Document number (ex: 12345678901 — exactly 11 digits): " DOCUMENT_NUMBER
read -p "Account ID (ex: 1): " ACCOUNT_ID
printf "Operation types: 1=PURCHASE, 2=INSTALLMENT PURCHASE, 3=WITHDRAWAL, 4=PAYMENT\n"
read -p "Operation type ID (1, 2, 3 or 4): " OPERATION_TYPE_ID
printf "Amount rules: types 1, 2, 3 require negative (ex: -50.00) | type 4 requires positive (ex: 50.00)\n"
echo "For Operation Type 1,2,3 (Ex: -50)"
echo "For Operation Type 4 (Ex: 50)"
read -p "Amount : " AMOUNT

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

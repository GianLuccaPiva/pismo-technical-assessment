package com.pismo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public class TransactionRequest {
    
    // @NotBlank + @Valid no Controller para não aceitar 
    // requests com null ou "" nos campos anotados em tempo de execução
    @NotBlank
    Long accountId;

    @NotBlank
    Long operationTypeId;

    BigDecimal amount;
    
    public TransactionRequest() {}

    public TransactionRequest(Long accountId, Long operationTypeId, BigDecimal amount) {
    this.accountId = accountId;
    this.operationTypeId = operationTypeId;
    this.amount = amount;
}

    public Long getAccountId() { return this.accountId; }
    public Long getOperationTypeId() { return this.operationTypeId; }
    public BigDecimal getAmount() { return this.amount; }



}

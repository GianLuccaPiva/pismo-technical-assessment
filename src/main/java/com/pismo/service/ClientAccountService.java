package com.pismo.service;

import org.springframework.stereotype.Service;

import com.pismo.dto.ClientAccountRequest;
import com.pismo.dto.ClientAccountResponse;
import com.pismo.model.ClientAccountModel;
import com.pismo.repository.ClientAccountRepo;

@Service
public class ClientAccountService {

    private ClientAccountRepo clientAccountRepo;

    public ClientAccountService(ClientAccountRepo clientAccountRepo) {
        this.clientAccountRepo = clientAccountRepo;
    }

    public ClientAccountResponse createAccount(ClientAccountRequest request) {

        if (clientAccountRepo.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new RuntimeException("Invalid Data");
            // Erro genérico para não informar que o número do documento
            // já está no nosso banco de dados
        }

        ClientAccountModel account = new ClientAccountModel();
        account.setDocumentNumber(request.getDocumentNumber());

        ClientAccountModel saved = clientAccountRepo.save(account);

        return new ClientAccountResponse(saved.getAccountId(), saved.getDocumentNumber());

    }

    public ClientAccountResponse getAccount(Long accountId) {

        ClientAccountModel account = clientAccountRepo.findById(accountId)
        .orElseThrow(() -> new RuntimeException("No Response"));

        return new ClientAccountResponse(account.getAccountId(), account.getDocumentNumber());

    }
    
}

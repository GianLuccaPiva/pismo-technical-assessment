package com.pismo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.pismo.dto.ClientAccountRequest;
import com.pismo.dto.ClientAccountResponse;
import com.pismo.service.ClientAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class ClientAccountController {

    private ClientAccountService clientAccountService;
    
    public ClientAccountController(ClientAccountService clientAccountService) {
        this.clientAccountService = clientAccountService;
    }

    @PostMapping
    public ResponseEntity<ClientAccountResponse> createAccount(
        @RequestBody
        @Valid
        ClientAccountRequest request) {

            ClientAccountResponse response = clientAccountService.createAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClientAccountResponse> getAccount(
        @PathVariable 
        Long id) {

        ClientAccountResponse response = clientAccountService.getAccount(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
        
}

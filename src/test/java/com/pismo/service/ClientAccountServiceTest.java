package com.pismo.service;

import com.pismo.dto.ClientAccountRequest;
import com.pismo.dto.ClientAccountResponse;
import com.pismo.model.ClientAccountModel;
import com.pismo.repository.ClientAccountRepo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class ClientAccountServiceTest {

    @Mock
    private ClientAccountRepo clientAccountRepo;

    @InjectMocks
    private ClientAccountService clientAccountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        ClientAccountRequest request = new ClientAccountRequest();
        request.setDocumentNumber("12345678901");

        ClientAccountModel saved = new ClientAccountModel();
        saved.setAccountId(1L);
        saved.setDocumentNumber("12345678901");

        when(clientAccountRepo.existsByDocumentNumber("12345678901")).thenReturn(false);
        when(clientAccountRepo.save(any(ClientAccountModel.class))).thenReturn(saved);

        ClientAccountResponse response = clientAccountService.createAccount(request);
        
        System.out.println("Account ID retornado: " + response.getAccountId());
        System.out.println("Document retornado: " + response.getDocumentNumber());

        assertNotNull(response);
        assertEquals(1L, response.getAccountId());
        assertEquals("12345678901", response.getDocumentNumber());

    }

    @Test
    void shouldRejectDocumentWithMoreOrLessThan11Digits() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    ClientAccountRequest request = new ClientAccountRequest();
    request.setDocumentNumber("123456789012"); // 12 dígitos

    Set<ConstraintViolation<ClientAccountRequest>> violations = validator.validate(request);

    assertFalse(violations.isEmpty());

    System.out.println("Violations encontradas: " + violations.size());
    violations.forEach(v -> System.out.println("Mensagem: " + v.getMessage()));
}

    @Test
    void shouldThrowExceptionWhenDocumentAlreadyExists() {
     
        ClientAccountRequest request = new ClientAccountRequest();
        request.setDocumentNumber("12345678901");

        when(clientAccountRepo.existsByDocumentNumber("12345678901")).thenReturn(true);

     
        assertThrows(RuntimeException.class, () -> {
            clientAccountService.createAccount(request);
        });
    }
    
}

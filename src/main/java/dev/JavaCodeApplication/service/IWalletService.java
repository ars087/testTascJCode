package dev.JavaCodeApplication.service;

import dev.JavaCodeApplication.entity.AccountWallet;
import dev.JavaCodeApplication.models.AccountWalletRequestDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IWalletService {
    Mono<ResponseEntity<String>> handleTransactionRequest(AccountWalletRequestDTO accountWallet);
}

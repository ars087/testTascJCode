package dev.JavaCodeApplication.service;

import dev.JavaCodeApplication.entity.AccountWallet;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IWalletService {
    Mono<ResponseEntity<String>> handleTransactionRequest(AccountWallet accountWallet);
}

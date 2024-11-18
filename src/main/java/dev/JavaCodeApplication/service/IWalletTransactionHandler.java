package dev.JavaCodeApplication.service;

import dev.JavaCodeApplication.entity.AccountWallet;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface IWalletTransactionHandler {
    Mono<ResponseEntity<String>> depositToWallet(AccountWallet accountWallet, BigDecimal amountRequest);

    Mono<ResponseEntity<String>> withdrawFromWallet(AccountWallet accountWallet, BigDecimal amountRequest);
}

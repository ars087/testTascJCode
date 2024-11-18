package dev.JavaCodeApplication.service.Impl;

import dev.JavaCodeApplication.entity.AccountWallet;
import dev.JavaCodeApplication.repository.AccountWalletRepository;
import dev.JavaCodeApplication.service.IWalletTransactionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;

@Service
public class WalletTransactionHandlerImpl implements IWalletTransactionHandler {

    private final AccountWalletRepository accountWalletRepository;
    private final TransactionalOperator transactionalOperator;

    public WalletTransactionHandlerImpl(AccountWalletRepository accountWalletRepository,
                                        TransactionalOperator transactionalOperator) {
        this.accountWalletRepository = accountWalletRepository;
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Mono<ResponseEntity<String>> depositToWallet(AccountWallet accountWallet, BigDecimal amountRequest) {

        return accountWalletRepository.depositToAccountWallet(accountWallet.getWalletId(), amountRequest)
            .as(transactionalOperator::transactional)
            .subscribeOn(Schedulers.boundedElastic())
            .then(Mono.fromCallable(() -> ResponseEntity.ok("Депозит пополнен")))
            .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка пополнения: " + throwable.getMessage())));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Mono<ResponseEntity<String>> withdrawFromWallet(AccountWallet accountWallet, BigDecimal amountRequest
    ) {

        return accountWalletRepository.withdrawFromAccountWallet(accountWallet.getWalletId(), amountRequest)
            .as(transactionalOperator::transactional)
            .subscribeOn(Schedulers.boundedElastic())
            .then(Mono.fromCallable(() -> ResponseEntity.ok("Перевод успешен")));
    }

}

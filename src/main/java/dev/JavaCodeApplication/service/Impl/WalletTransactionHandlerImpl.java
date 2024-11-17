package dev.JavaCodeApplication.service.Impl;

import dev.JavaCodeApplication.entity.AccountWallet;
import dev.JavaCodeApplication.repository.AccountWalletRepository;
import dev.JavaCodeApplication.service.IWalletTransactionHandler;
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
    public Mono<ResponseEntity<String>> depositToWallet(AccountWallet accountWallet, BigDecimal amountRequest,
                                                        BigDecimal balanceWalletFromDb) {
        BigDecimal newBalance = amountRequest.add(balanceWalletFromDb);
        accountWallet.setAmount(newBalance);
        return accountWalletRepository.save(accountWallet)
            .as(transactionalOperator::transactional)
            .subscribeOn(Schedulers.boundedElastic())
            .map(updatedAccount -> ResponseEntity.ok("Депозит пополнен"));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Mono<ResponseEntity<String>> withdrawFromWallet(AccountWallet accountWallet, BigDecimal amountRequest,
                                                           BigDecimal balanceWalletFromDb) {
        BigDecimal newBalance = balanceWalletFromDb.subtract(amountRequest);
        accountWallet.setAmount(newBalance);
        return accountWalletRepository.save(accountWallet)
            .as(transactionalOperator::transactional)
            .subscribeOn(Schedulers.boundedElastic())
            .map(updatedAccount -> ResponseEntity.ok("Перевод успешен"));
    }

}

package dev.JavaCodeApplication.repository;


import dev.JavaCodeApplication.entity.AccountWallet;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface AccountWalletRepository extends ReactiveCrudRepository<AccountWallet, UUID> {
    @Query("UPDATE account_wallet  SET amount = :amount  WHERE  wallet_id = :id")
    Mono<Void> depositToAccountWallet( UUID id, BigDecimal amount);


    @Query("UPDATE account_wallet  SET amount = :amount  WHERE  wallet_id = :id")
    Mono<Void> withdrawFromAccountWallet( UUID id,  BigDecimal amount);
}

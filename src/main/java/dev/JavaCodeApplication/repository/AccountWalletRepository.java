package dev.JavaCodeApplication.repository;


import dev.JavaCodeApplication.entity.AccountWallet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountWalletRepository extends ReactiveCrudRepository<AccountWallet, UUID> {
}

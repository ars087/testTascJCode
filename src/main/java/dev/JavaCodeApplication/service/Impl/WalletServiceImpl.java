package dev.JavaCodeApplication.service.Impl;

import dev.JavaCodeApplication.entity.AccountWallet;
import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.InsufficientFundsException;
import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.UnsupportedOperationException;
import dev.JavaCodeApplication.repository.AccountWalletRepository;
import dev.JavaCodeApplication.service.IWalletService;
import dev.JavaCodeApplication.service.IWalletTransactionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements IWalletService {
    private final AccountWalletRepository accountWalletRepository;
    private final IWalletTransactionHandler iWalletTransactionHandler;


    public WalletServiceImpl(AccountWalletRepository accountWalletRepository,
                             IWalletTransactionHandler iWalletTransactionHandler) {
        this.accountWalletRepository = accountWalletRepository;


        this.iWalletTransactionHandler = iWalletTransactionHandler;
    }

    @Override
    public Mono<ResponseEntity<String>> handleTransactionRequest(AccountWallet accountWallet) {
        BigDecimal amountRequest = accountWallet.getAmount();


        return accountWalletRepository.findById(accountWallet.getWalletId())
            .flatMap(accountWalletFromDb -> {
                BigDecimal balanceWalletFromDb = accountWalletFromDb.getAmount();
                switch (accountWallet.getOperationType()) {
                    case DEPOSIT -> {
                        return iWalletTransactionHandler.depositToWallet(accountWallet, amountRequest,
                            balanceWalletFromDb);
                    }
                    case WITHDRAW -> {

                        if (amountRequest.compareTo(balanceWalletFromDb) > 0) {
                            return Mono.error(new InsufficientFundsException("Не достаточно средств для перевода"));
                        }

                        return iWalletTransactionHandler.withdrawFromWallet(accountWallet, amountRequest,
                            balanceWalletFromDb);
                    }
                    default -> {
                        return Mono.error(new UnsupportedOperationException("Операция не поддерживается"));
                    }

                }

            });

    }
}

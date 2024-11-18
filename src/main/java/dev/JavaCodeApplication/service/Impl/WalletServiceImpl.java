package dev.JavaCodeApplication.service.Impl;

import dev.JavaCodeApplication.entity.AccountWallet;
import dev.JavaCodeApplication.entity.enums.OperationType;
import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.InsufficientFundsException;
import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.UnsupportedOperationException;
import dev.JavaCodeApplication.models.AccountWalletRequestDTO;
import dev.JavaCodeApplication.repository.AccountWalletRepository;
import dev.JavaCodeApplication.service.IWalletService;
import dev.JavaCodeApplication.service.IWalletTransactionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static dev.JavaCodeApplication.entity.enums.OperationType.DEPOSIT;
import static dev.JavaCodeApplication.entity.enums.OperationType.WITHDRAW;

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
    public Mono<ResponseEntity<String>> handleTransactionRequest(AccountWalletRequestDTO accountWalletDto) {
        BigDecimal amountRequest = accountWalletDto.getAmount();

        AccountWallet accountWallet = new AccountWallet();

        accountWallet.setWalletId(UUID.fromString(accountWalletDto.getWalletId()));
        accountWallet.setAmount(accountWalletDto.getAmount());

        return accountWalletRepository.findById(accountWallet.getWalletId())
            .flatMap(accountWalletFromDb -> {
                BigDecimal balanceWalletFromDb = accountWalletFromDb.getAmount();
                if (accountWalletDto.getOperationType().equals("DEPOSIT")) {


                        return iWalletTransactionHandler.depositToWallet(accountWallet,accountWalletFromDb.getAmount().add(amountRequest) );
                    }
                    else {

                        if (amountRequest.compareTo(balanceWalletFromDb) > 0) {
                            return Mono.error(new InsufficientFundsException("Не достаточно средств для перевода"));
                        }
                        return iWalletTransactionHandler.withdrawFromWallet(accountWallet,accountWalletFromDb.getAmount().subtract(amountRequest)  );
                    }

            });

    }
}

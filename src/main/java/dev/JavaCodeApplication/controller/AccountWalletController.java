package dev.JavaCodeApplication.controller;

import dev.JavaCodeApplication.entity.AccountWallet;
import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.WalletNotFoundException;
import dev.JavaCodeApplication.models.AccountWalletRequestDTO;
import dev.JavaCodeApplication.repository.AccountWalletRepository;
import dev.JavaCodeApplication.service.IWalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@Validated
@RequestMapping(path = "${endpoint.global}")
public class AccountWalletController {

    private final AccountWalletRepository accountWalletRepository;
    private final IWalletService walletService;

    public AccountWalletController(AccountWalletRepository accountWalletRepository, IWalletService walletService) {
        this.accountWalletRepository = accountWalletRepository;
        this.walletService = walletService;
    }

    @PostMapping(value = "${endpoint.deposit-withdraw}")
    public Mono<ResponseEntity<String>> withdrawAndDeposit(
        @Valid @RequestBody AccountWalletRequestDTO accountWalletDto) {

        return walletService.handleTransactionRequest(accountWalletDto)
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())))
            .switchIfEmpty(Mono.error(new WalletNotFoundException("Кошелек не найден")));

    }

    @GetMapping("${endpoint.get-balance}")
    public Mono<ResponseEntity<AccountWallet>> getBalance(
        @PathVariable @NotNull @Size(min = 36, max = 36, message = "Не верный формат ID") String wallets_uuid) {
        return accountWalletRepository.findById(UUID.fromString(wallets_uuid))
            .map(ResponseEntity::ok)
            .switchIfEmpty(Mono.error(new WalletNotFoundException("Кошелек не найден")));

    }
}
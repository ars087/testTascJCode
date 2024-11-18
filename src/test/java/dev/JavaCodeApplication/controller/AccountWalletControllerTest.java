package dev.JavaCodeApplication.controller;

import dev.JavaCodeApplication.entity.AccountWallet;
import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.WalletNotFoundException;
import dev.JavaCodeApplication.models.AccountWalletRequestDTO;
import dev.JavaCodeApplication.repository.AccountWalletRepository;
import dev.JavaCodeApplication.service.IWalletService;
import dev.JavaCodeApplication.service.IWalletTransactionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
public class AccountWalletControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private AccountWalletRepository accountWalletRepository;
    @MockBean
    private IWalletService walletService;
    @MockBean
    private IWalletTransactionHandler iWalletTransactionHandler;

    @Test
    public void testWithdrawAndDeposit_success() {
        AccountWalletRequestDTO requestDTO = new AccountWalletRequestDTO();
        requestDTO.setAmount(new BigDecimal("100.0"));
        requestDTO.setWalletId(UUID.randomUUID().toString());
        requestDTO.setOperationType("DEPOSIT");

        when(walletService.handleTransactionRequest(any()))
            .thenReturn(Mono.just(ResponseEntity.ok().body("Transaction successful")));

        webTestClient.post()
            .uri("http://localhost:8080/api/v1/wallet") // Замените на актуальный эндпоинт
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestDTO)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Transaction successful");
    }

    @Test
    public void testGetBalance_success() {
        UUID walletId = UUID.randomUUID();
        AccountWallet accountWallet = new AccountWallet();
        accountWallet.setWalletId(walletId);
        accountWallet.setAmount(new BigDecimal("100.0"));

        when(accountWalletRepository.findById(walletId)).thenReturn(Mono.just(accountWallet));

        webTestClient.get()
            .uri("http://localhost:8080/api/v1/wallets/" + walletId) // Замените на актуальный эндпоинт
            .exchange()
            .expectStatus().isOk()
            .expectBody(AccountWallet.class)
            .consumeWith(response -> {
                AccountWallet responseBody = response.getResponseBody();
                assert responseBody != null;
                assert responseBody.getWalletId().equals(walletId);
                assert responseBody.getAmount().compareTo(new BigDecimal("100.0")) == 0;
            });
    }

    @Test
    public void testGetBalance_walletNotFound() {
        UUID walletId = UUID.randomUUID();

        when(accountWalletRepository.findById(walletId)).thenReturn(Mono.empty());

        webTestClient.get()
            .uri("http://localhost:8080/api/v1/wallets/" + walletId.toString())
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(String.class)
            .isEqualTo("Кошелек не найден");
    }
}
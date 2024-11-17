package dev.JavaCodeApplication.exeptions.handler;

import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.InsufficientFundsException;
import dev.JavaCodeApplication.exeptions.service_wallet_exceptinons.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionsHandler {


    @ExceptionHandler(WalletNotFoundException.class)
    public Mono<ResponseEntity<String>> handleWalletNotFoundException(WalletNotFoundException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public Mono<ResponseEntity<String>> handleInsufficientFundsException(InsufficientFundsException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleGenericException(Exception e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
    }

}

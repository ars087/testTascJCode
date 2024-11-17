package dev.JavaCodeApplication.exeptions.service_wallet_exceptinons;

public class InsufficientFundsException extends WalletServiceException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
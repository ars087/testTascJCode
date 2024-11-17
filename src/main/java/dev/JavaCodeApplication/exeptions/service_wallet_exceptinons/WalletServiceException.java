package dev.JavaCodeApplication.exeptions.service_wallet_exceptinons;

public class WalletServiceException extends RuntimeException {
    public WalletServiceException(String message) {
        super(message);
    }
}
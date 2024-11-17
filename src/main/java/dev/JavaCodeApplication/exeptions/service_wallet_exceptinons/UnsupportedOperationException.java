package dev.JavaCodeApplication.exeptions.service_wallet_exceptinons;

public class UnsupportedOperationException extends  WalletServiceException {
    public UnsupportedOperationException(String message) {
        super(message);
    }
}
